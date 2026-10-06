package com.hotel.shmrs.service;

import com.hotel.shmrs.builder.BookingBuilder;
import com.hotel.shmrs.dao.BookingDAO;
import com.hotel.shmrs.dao.InMemoryBookingDAO;
import com.hotel.shmrs.exceptions.InvalidPaymentException;
import com.hotel.shmrs.exceptions.RoomNotAvailableException;
import com.hotel.shmrs.model.entity.Booking;
import com.hotel.shmrs.model.entity.Customer;
import com.hotel.shmrs.model.entity.HotelService;
import com.hotel.shmrs.model.entity.Room;
import com.hotel.shmrs.model.enums.BookingStatus;
import com.hotel.shmrs.observer.EmailNotification;
import com.hotel.shmrs.observer.NotificationObserver;
import com.hotel.shmrs.observer.SMSNotification;
import com.hotel.shmrs.strategy.PaymentStrategy;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class HotelReservationEngine {
    private final Map<Integer, Room> inventory = new ConcurrentHashMap<>();
    private final BookingDAO bookingDAO = new InMemoryBookingDAO();
    private final List<NotificationObserver> observers = new CopyOnWriteArrayList<>();

    // Concurrent Queues & Undo Data Structures
    private final Queue<Customer> standardCheckInQueue = new LinkedList<>();
    private final PriorityQueue<Customer> vipCheckInQueue = new PriorityQueue<>(
            (c1, c2) -> Boolean.compare(c2.isVIP(), c1.isVIP())
    );
    private final Stack<Booking> cancellationUndoStack = new Stack<>();

    public HotelReservationEngine() {
        observers.add(new EmailNotification());
        observers.add(new SMSNotification());
    }

    public void registerRoom(Room room) {
        inventory.put(room.getRoomNumber(), room);
    }

    public BookingDAO getDAO() {
        return bookingDAO;
    }

    // Java 8 Streams & Method References
    public List<Room> filterAvailableRoomsSortedByPrice() {
        return inventory.values().stream()
                .filter(Room::isAvailable)
                .sorted(Comparator.comparingDouble(r -> r.calculatePrice(1)))
                .collect(Collectors.toList());
    }

    // Fine-grained Room Monitor Locking for Concurrent Reservations
    public Booking bookRoom(String bookingId, Customer customer, int roomNumber, int nights, PaymentStrategy payment)
            throws RoomNotAvailableException, InvalidPaymentException {

        Room targetRoom = inventory.get(roomNumber);
        if (targetRoom == null) {
            throw new IllegalArgumentException("Invalid room number provided: #" + roomNumber);
        }

        synchronized (targetRoom) {
            if (!targetRoom.isAvailable()) {
                throw new RoomNotAvailableException("Room #" + roomNumber + " is already occupied!");
            }

            // Immediately mark unavailable to block parallel race threads
            targetRoom.setAvailable(false);

            try {
                double addOnTotal = customer.getOptedServices().stream()
                        .mapToDouble(HotelService::getCost)
                        .sum();
                double finalCost = targetRoom.calculatePrice(nights) + addOnTotal;

                payment.executePayment(finalCost);

                Booking booking = new BookingBuilder()
                        .setBookingId(bookingId)
                        .setCustomer(customer)
                        .setRoom(targetRoom)
                        .setNights(nights)
                        .setTotalCost(finalCost)
                        .setPaymentMode(payment.getMode())
                        .setStatus(BookingStatus.CONFIRMED)
                        .build();

                bookingDAO.save(booking);

                observers.forEach(obs -> obs.notifyUser(customer.getName(), 
                        "Reservation Confirmed! Ref #" + bookingId + " for Room #" + roomNumber + ". Total: ₹" + finalCost));

                return booking;

            } catch (Exception e) {
                targetRoom.setAvailable(true); // Rollback on failure
                throw e;
            }
        }
    }

    // Wait-Notify Synchronization for Cancellations
    public synchronized void cancelBooking(String bookingId) {
        Booking booking = bookingDAO.findById(bookingId);
        if (booking != null && booking.getStatus() == BookingStatus.CONFIRMED) {
            booking.setStatus(BookingStatus.CANCELLED);
            booking.getRoom().setAvailable(true);
            cancellationUndoStack.push(booking);

            System.out.println("[Cancellation Engine] Booking #" + bookingId + " cancelled. Room #" + 
                               booking.getRoom().getRoomNumber() + " marked Available.");

            notifyAll(); // Wake up any waiting customer threads
            observers.forEach(obs -> obs.notifyUser(booking.getCustomer().getName(), 
                    "Booking #" + bookingId + " has been cancelled. Refund initiated."));
        }
    }

    public void enqueueCustomer(Customer c) {
        if (c.isVIP()) {
            vipCheckInQueue.offer(c);
        } else {
            standardCheckInQueue.offer(c);
        }
    }

    public void processNextCheckIn() {
        Customer next = !vipCheckInQueue.isEmpty() ? vipCheckInQueue.poll() : standardCheckInQueue.poll();
        if (next != null) {
            System.out.println("[Front Desk Check-In Queue] Now serving guest: " + next.getName() + " (VIP: " + next.isVIP() + ")");
        }
    }
}
