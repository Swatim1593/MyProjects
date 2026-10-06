package com.movieticket.service;

import com.movieticket.enums.*;
import com.movieticket.model.*;
import com.movieticket.notification.*;
import com.movieticket.repository.DataRepository;
import com.movieticket.strategy.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class BookingService {
    private final DataRepository repository;
    private final NotificationService emailNotifier;
    private final NotificationService smsNotifier;
    private static final int LOCK_TIMEOUT_MINUTES = 5;

    public BookingService(DataRepository repository) {
        this.repository = repository;
        this.emailNotifier = new EmailNotificationService();
        this.smsNotifier = new SMSNotificationService();
    }

    public Optional<Booking> reserveSeats(String userId, String showId, List<String> seatIds) {
        User user = repository.users.get(userId);
        Show show = repository.shows.get(showId);

        if (user == null || !(user instanceof Customer) || show == null) {
            System.out.println("Booking Refused: User must be an authenticated Customer and Show must exist.");
            return Optional.empty();
        }

        ReentrantLock showLock = show.getShowLock();
        showLock.lock();
        try {
            Map<String, ShowSeat> showSeats = show.getShowSeats();

            showSeats.values().stream()
                     .filter(seat -> seat.isLockExpired(LOCK_TIMEOUT_MINUTES))
                     .forEach(ShowSeat::releaseLock);

            boolean allAvailable = seatIds.stream().allMatch(seatId -> {
                ShowSeat seat = showSeats.get(seatId);
                return seat != null && seat.getStatus() == SeatStatus.AVAILABLE;
            });

            if (!allAvailable) {
                System.out.println("Lock Failed for User [" + user.getName() + "]: Requested seat(s) " + seatIds + " ALREADY LOCKED/BOOKED.");
                show.getWaitingListQueue().add(userId);
                System.out.println("-> Customer [" + user.getName() + "] enqueued into Waiting List for Show: " + showId);
                return Optional.empty();
            }

            List<ShowSeat> reservedSeats = new ArrayList<>();
            for (String seatId : seatIds) {
                ShowSeat seat = showSeats.get(seatId);
                seat.lockSeat(userId);
                reservedSeats.add(seat);
            }

            double totalAmount = reservedSeats.stream().mapToDouble(ShowSeat::getPrice).sum();
            String bookingId = "BK-" + UUID.randomUUID().toString().substring(0, 8);

            Booking booking = new Booking(bookingId, (Customer) user, show, reservedSeats, totalAmount);
            repository.bookings.put(bookingId, booking);
            ((Customer) user).addBooking(booking);

            System.out.println("SUCCESS: Seats " + seatIds + " locked for 5 mins for User [" + user.getName() + "]. Booking ID: " + bookingId);
            return Optional.of(booking);

        } finally {
            showLock.unlock();
        }
    }

    public Optional<Ticket> confirmBooking(String bookingId, PaymentMethod paymentMethod) {
        Booking booking = repository.bookings.get(bookingId);
        if (booking == null || booking.getStatus() != BookingStatus.PENDING) {
            System.out.println("Confirmation Failed: Invalid or non-pending booking.");
            return Optional.empty();
        }

        PaymentStrategy paymentStrategy = PaymentStrategyFactory.getStrategy(paymentMethod);
        boolean paymentSuccess = paymentStrategy.processPayment(bookingId, booking.getTotalAmount());

        ReentrantLock showLock = booking.getShow().getShowLock();
        showLock.lock();
        try {
            if (paymentSuccess) {
                booking.setStatus(BookingStatus.CONFIRMED);
                booking.getBookedSeats().forEach(seat -> seat.setStatus(SeatStatus.BOOKED));

                Payment payment = new Payment("PAY-" + UUID.randomUUID().toString().substring(0, 8), bookingId, booking.getTotalAmount(), paymentMethod);
                payment.setStatus(PaymentStatus.SUCCESSFUL);
                booking.setPayment(payment);

                String ticketId = "TCK-" + UUID.randomUUID().toString().substring(0, 8);
                List<String> seatIds = booking.getBookedSeats().stream().map(ShowSeat::getSeatId).collect(Collectors.toList());

                Ticket ticket = new Ticket(ticketId, bookingId, booking.getShow().getMovie().getTitle(),
                        booking.getShow().getTheatre().getName(), seatIds, booking.getShow().getStartTime());

                booking.setTicket(ticket);

                String msg = "Booking Confirmed! Ticket ID: " + ticketId + " for Movie: " + booking.getShow().getMovie().getTitle();
                emailNotifier.sendNotification(booking.getCustomer(), msg);
                smsNotifier.sendNotification(booking.getCustomer(), msg);

                return Optional.of(ticket);
            } else {
                booking.setStatus(BookingStatus.CANCELLED);
                booking.getBookedSeats().forEach(ShowSeat::releaseLock);
                System.out.println("Payment Failed. Seat locks released for Booking ID: " + bookingId);
                return Optional.empty();
            }
        } finally {
            showLock.unlock();
        }
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = repository.bookings.get(bookingId);
        if (booking == null || booking.getStatus() != BookingStatus.CONFIRMED) {
            System.out.println("Cancellation Failed: Booking not found or not confirmed.");
            return false;
        }

        if (LocalDateTime.now().isAfter(booking.getShow().getStartTime())) {
            System.out.println("Cancellation Failed: Show has already started/completed.");
            return false;
        }

        ReentrantLock showLock = booking.getShow().getShowLock();
        showLock.lock();
        try {
            booking.setStatus(BookingStatus.CANCELLED);
            booking.getBookedSeats().forEach(ShowSeat::releaseLock);

            PaymentStrategy paymentStrategy = PaymentStrategyFactory.getStrategy(PaymentMethod.UPI);
            paymentStrategy.processRefund(bookingId, booking.getTotalAmount());

            System.out.println("SUCCESS: Booking " + bookingId + " cancelled and seats released.");

            ConcurrentLinkedQueue<String> queue = booking.getShow().getWaitingListQueue();
            if (!queue.isEmpty()) {
                String nextUserId = queue.poll();
                User nextUser = repository.users.get(nextUserId);
                if (nextUser != null) {
                    emailNotifier.sendNotification(nextUser, "PRIORITY ALERT: Seats released for Show: "
                            + booking.getShow().getMovie().getTitle() + "! Book now.");
                }
            }
            return true;
        } finally {
            showLock.unlock();
        }
    }
}