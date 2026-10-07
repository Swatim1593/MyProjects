package com.movieticket.facade;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.movieticket.enums.PaymentMethod;
import com.movieticket.model.Booking;
import com.movieticket.model.Customer;
import com.movieticket.model.Movie;
import com.movieticket.model.Screen;
import com.movieticket.model.Show;
import com.movieticket.model.Theatre;
import com.movieticket.model.Ticket;
import com.movieticket.service.BookingService;

@ExtendWith(MockitoExtension.class)
class BookingFacadeTest {

    @Mock
    private BookingService bookingService;

    @Test
    @DisplayName("Facade returns empty when booking reservation fails")
    void testBookMovieTicketWithAddonsReservationFailed() {
        when(bookingService.reserveSeats(anyString(), anyString(), anyList())).thenReturn(Optional.empty());

        BookingFacade facade = new BookingFacade(bookingService);
        Optional<Ticket> res = facade.bookMovieTicketWithAddons("USR-1", "SH-1",
                Collections.singletonList("A1"), PaymentMethod.UPI, false, false, false);

        assertFalse(res.isPresent());
        verify(bookingService, never()).confirmBooking(anyString(), any());
    }

    @Test
    @DisplayName("Facade applies all add-on decorators and confirms booking")
    void testBookMovieTicketWithAllAddons() {
        Customer customer = new Customer("USR-1", "Alice", "a@a.com", "123", "p");
        Booking booking = new Booking("BK-1", customer, null, Collections.emptyList(), 200.0);

        Ticket dummyTicket = new Ticket("TCK-1", "BK-1", "Movie", "Theatre",
                Collections.singletonList("A1"), LocalDateTime.now());

        when(bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1")))
                .thenReturn(Optional.of(booking));
        when(bookingService.confirmBooking("BK-1", PaymentMethod.UPI))
                .thenReturn(Optional.of(dummyTicket));

        BookingFacade facade = new BookingFacade(bookingService);

        // Base (200) + Popcorn (150) + Glasses (30) + Insurance (20) = 400.0
        Optional<Ticket> result = facade.bookMovieTicketWithAddons("USR-1", "SH-1",
                Collections.singletonList("A1"), PaymentMethod.UPI, true, true, true);

        assertTrue(result.isPresent());
        assertEquals(400.0, booking.getTotalAmount());
        assertEquals(dummyTicket, result.get());
    }

    @Test
    @DisplayName("Facade works with zero add-ons selected")
    void testBookMovieTicketWithNoAddons() {
        Customer customer = new Customer("USR-1", "Alice", "a@a.com", "123", "p");
        Booking booking = new Booking("BK-1", customer, null, Collections.emptyList(), 200.0);

        when(bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1")))
                .thenReturn(Optional.of(booking));
        when(bookingService.confirmBooking("BK-1", PaymentMethod.CREDIT_CARD))
                .thenReturn(Optional.empty());

        BookingFacade facade = new BookingFacade(bookingService);

        Optional<Ticket> result = facade.bookMovieTicketWithAddons("USR-1", "SH-1",
                Collections.singletonList("A1"), PaymentMethod.CREDIT_CARD, false, false, false);

        assertFalse(result.isPresent());
        assertEquals(200.0, booking.getTotalAmount());
    }
}