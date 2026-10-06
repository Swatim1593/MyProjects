package com.movieticket.facade;

import com.movieticket.decorator.*;
import com.movieticket.enums.PaymentMethod;
import com.movieticket.model.Booking;
import com.movieticket.model.Ticket;
import com.movieticket.service.BookingService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class BookingFacade {
    private final BookingService bookingService;

    public BookingFacade(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    
    /*Optional<Ticket> ticketOpt = bookingFacade.bookMovieTicketWithAddons(
                    winningUserId,
                    "SH-501",
                    Collections.singletonList("A1"),
                    PaymentMethod.UPI,
                    true,  // Add Popcorn
                    true,  // Add 3D Glasses
                    false  // Add Insurance
            );
*/

    public Optional<Ticket> bookMovieTicketWithAddons(
            String userId,
            String showId,
            List<String> seatIds,
            PaymentMethod paymentMethod,
            boolean addPopcorn,
            boolean addGlasses,
            boolean addInsurance) {

        System.out.println("\n[FACADE] Orchestrating Ticket Reservation Workflow...");

        Optional<Booking> bookingOpt = bookingService.reserveSeats(userId, showId, seatIds);
        if (!bookingOpt.isPresent()) {
            return Optional.empty();
        }

        Booking booking = bookingOpt.get();

        TicketComponent itemizedBill = new BaseTicket(seatIds.toString(), booking.getTotalAmount());
        if (addPopcorn) {
            itemizedBill = new PopcornComboDecorator(itemizedBill);
        }
        if (addGlasses) {
            itemizedBill = new Glasses3DDecorator(itemizedBill);
        }
        if (addInsurance) {
            itemizedBill = new CancellationInsuranceDecorator(itemizedBill);
        }

        booking.setTotalAmount(itemizedBill.getCost());

        System.out.println("[FACADE] Itemized Bill: " + itemizedBill.getDescription());
        System.out.println("[FACADE] Final Price (Seats + Addons): ₹" + itemizedBill.getCost());

        return bookingService.confirmBooking(booking.getBookingId(), paymentMethod);
    }
}