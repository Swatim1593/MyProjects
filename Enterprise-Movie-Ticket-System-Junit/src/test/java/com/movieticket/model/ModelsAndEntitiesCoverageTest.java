package com.movieticket.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.Collections;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.movieticket.enums.BookingStatus;
import com.movieticket.enums.Genre;
import com.movieticket.enums.PaymentMethod;
import com.movieticket.enums.PaymentStatus;
import com.movieticket.enums.SeatStatus;
import com.movieticket.enums.SeatType;
import com.movieticket.enums.UserRole;
import com.movieticket.notification.EmailNotificationService;
import com.movieticket.notification.SMSNotificationService;

class ModelsAndEntitiesCoverageTest {

    @Test
    @DisplayName("User and Admin permissions and authentication checks")
    void testAdminAndUser() {
        Admin admin = new Admin("ADM-1", "Boss", "boss@corp.com", "12345", "hashedSecret");
        assertEquals("ADM-1", admin.getId());
        assertEquals("Boss", admin.getName());
        assertEquals("boss@corp.com", admin.getEmail());
        assertEquals("12345", admin.getPhone());
        assertEquals(UserRole.ADMIN, admin.getRole());
        assertTrue(admin.validatePassword("hashedSecret"));
        assertFalse(admin.validatePassword("wrongPass"));

        assertTrue(admin.hasPermissions("MANAGE_MOVIES"));
        assertTrue(admin.hasPermissions("MANAGE_SHOWS"));
        assertTrue(admin.hasPermissions("VIEW_REPORTS"));
        assertFalse(admin.hasPermissions("DELETE_DATABASE"));
        assertNotNull(admin.toString());
    }

    @Test
    @DisplayName("Customer and Booking relationships")
    void testCustomerAndBooking() {
        Customer customer = new Customer("C-1", "John", "j@j.com", "987", "pass");
        Booking booking = new Booking("B-1", customer, null, Collections.emptyList(), 150.0);

        customer.addBooking(booking);
        assertEquals(1, customer.getBookings().size());
        assertEquals(booking, customer.getBookings().get(0));

        booking.setTotalAmount(250.0);
        assertEquals(250.0, booking.getTotalAmount());

        booking.setStatus(BookingStatus.CONFIRMED);
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());

        Ticket ticket = new Ticket("T-1", "B-1", "Title", "Theatre", Collections.singletonList("A1"), LocalDateTime.now());
        booking.setTicket(ticket);
        assertNotNull(booking.toString());
        assertNotNull(ticket.toString());
    }

    @Test
    @DisplayName("Movie and Builder completeness")
    void testMovieAndBuilder() {
        LocalDateTime now = LocalDateTime.now();
        Movie movie = new Movie.MovieBuilder("M-1", "Inception", "English", Genre.SCI_FI)
                .duration(148)
                .rating(8.8)
                .releaseDate(now)
                .director("Christopher Nolan")
                .build();

        assertEquals("M-1", movie.getId());
        assertEquals("Inception", movie.getTitle());
        assertEquals("English", movie.getLanguage());
        assertEquals(Genre.SCI_FI, movie.getGenre());
        assertEquals(8.8, movie.getRating());
        assertNotNull(movie.toString());
    }

    @Test
    @DisplayName("ShowSeat lock lifecycle and timeout checks")
    void testShowSeatLifecycle() {
        ShowSeat seat = new ShowSeat("A1", SeatType.VIP, 500.0);
        assertEquals("A1", seat.getSeatId());
        assertEquals(SeatStatus.AVAILABLE, seat.getStatus());
        assertEquals(500.0, seat.getPrice());

        // Not locked -> isLockExpired is false
        assertFalse(seat.isLockExpired(5));

        seat.lockSeat("USR-1");
        assertEquals(SeatStatus.LOCKED, seat.getStatus());

        // Timestamp is null initially -> isLockExpired is false
        assertFalse(seat.isLockExpired(5));

        seat.releaseLock();
        assertEquals(SeatStatus.AVAILABLE, seat.getStatus());

        seat.setStatus(SeatStatus.BOOKED);
        assertEquals(SeatStatus.BOOKED, seat.getStatus());
        assertNotNull(seat.toString());
    }

    @Test
    @DisplayName("Screen, Seat, and Theatre models")
    void testTheatreScreenSeat() {
        Seat seat = new Seat("S1", 1, 2, SeatType.REGULAR,150.0);
        assertEquals("S1", seat.getSeatId());
        assertEquals(SeatType.REGULAR, seat.getSeatType());
        assertEquals(150.0, seat.getPrice());
        assertNotNull(seat.toString());

        Screen screen = new Screen("SCR-1", "Audi-1", Collections.singletonList(seat));
        assertEquals("SCR-1", screen.getId());
        assertEquals("Audi-1", screen.getName());
        assertEquals(1, screen.getSeats().size());
        assertNotNull(screen.toString());

        Theatre theatre = new Theatre("TH-1", "PVR", "Bengaluru", "MG Road", Collections.singletonList(screen));
        assertEquals("TH-1", theatre.getId());
        assertEquals("PVR", theatre.getName());
        assertEquals("Bengaluru", theatre.getCity());
        assertEquals("MG Road", theatre.getLocation());
        assertEquals(1, theatre.getScreens().size());
        assertNotNull(theatre.toString());
    }

    @Test
    @DisplayName("Show getters and internals")
    void testShow() {
        Movie movie = new Movie.MovieBuilder("M1", "Title", "Lang", Genre.DRAMA).build();
        Screen screen = new Screen("S1", "Main", Collections.emptyList());
        Theatre theatre = new Theatre("T1", "Th", "City", "Loc", Collections.singletonList(screen));
        LocalDateTime now = LocalDateTime.now();

        Show show = new Show("SH-1", movie, theatre, screen, now, now.plusHours(2));
        assertEquals("SH-1", show.getId());
        assertEquals(movie, show.getMovie());
        assertEquals(theatre, show.getTheatre());
        assertEquals(now, show.getStartTime());
        assertNotNull(show.getShowSeats());
        assertNotNull(show.getWaitingListQueue());
        assertNotNull(show.getShowLock());
        assertNotNull(show.toString());
    }

    @Test
    @DisplayName("Payment model status transitions")
    void testPayment() {
        Payment payment = new Payment("P1", "B1", 100.0, PaymentMethod.UPI);
        assertEquals(PaymentStatus.PENDING, payment.getStatus());

        payment.setStatus(PaymentStatus.SUCCESSFUL);
        assertEquals(PaymentStatus.SUCCESSFUL, payment.getStatus());

        Booking booking = new Booking("B1", null, null, Collections.emptyList(), 100.0);
        booking.setPayment(payment);
        assertNotNull(booking.toString());
    }

    @Test
    @DisplayName("Notification services output coverage")
    void testNotificationServices() {
        Customer customer = new Customer("C1", "Jane", "jane@test.com", "1234567890", "pwd");
        EmailNotificationService emailService = new EmailNotificationService();
        SMSNotificationService smsService = new SMSNotificationService();

        assertDoesNotThrow(() -> emailService.sendNotification(customer, "Hello"));
        assertDoesNotThrow(() -> smsService.sendNotification(customer, "Hello"));
    }

    @Test
    @DisplayName("Enum values coverage for full branch metrics")
    void testEnums() {
        for (BookingStatus status : BookingStatus.values()) {
            assertNotNull(BookingStatus.valueOf(status.name()));
        }
        for (Genre genre : Genre.values()) {
            assertNotNull(Genre.valueOf(genre.name()));
        }
        for (PaymentMethod method : PaymentMethod.values()) {
            assertNotNull(PaymentMethod.valueOf(method.name()));
        }
        for (PaymentStatus status : PaymentStatus.values()) {
            assertNotNull(PaymentStatus.valueOf(status.name()));
        }
        for (SeatStatus status : SeatStatus.values()) {
            assertNotNull(SeatStatus.valueOf(status.name()));
        }
        for (SeatType type : SeatType.values()) {
            assertNotNull(SeatType.valueOf(type.name()));
        }
        for (UserRole role : UserRole.values()) {
            assertNotNull(UserRole.valueOf(role.name()));
        }
    }
}