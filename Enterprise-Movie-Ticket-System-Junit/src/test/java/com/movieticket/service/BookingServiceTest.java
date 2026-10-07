package com.movieticket.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.movieticket.enums.BookingStatus;
import com.movieticket.enums.Genre;
import com.movieticket.enums.PaymentMethod;
import com.movieticket.enums.SeatStatus;
import com.movieticket.enums.SeatType;
import com.movieticket.model.Admin;
import com.movieticket.model.Booking;
import com.movieticket.model.Customer;
import com.movieticket.model.Movie;
import com.movieticket.model.Screen;
import com.movieticket.model.Seat;
import com.movieticket.model.Show;
import com.movieticket.model.ShowSeat;
import com.movieticket.model.Theatre;
import com.movieticket.model.Ticket;
import com.movieticket.repository.DataRepository;

class BookingServiceTest {

	private DataRepository repository;
	private BookingService bookingService;
	private Customer customer;
	private Admin admin;
	private Show show;
	private Movie movie;
	private Theatre theatre;

	@BeforeEach
	void setUp() {
		repository = new DataRepository();
		bookingService = new BookingService(repository);

		customer = new Customer("USR-1", "John Doe", "john@example.com", "9999999999", "pass123");
		admin = new Admin("ADM-1", "Admin Boss", "admin@example.com", "8888888888", "adminPass");
		repository.users.put(customer.getId(), customer);
		repository.users.put(admin.getId(), admin);

		movie = new Movie.MovieBuilder("M-1", "KGF-3", "Kannada", Genre.ACTION).build();
		repository.movies.put(movie.getId(), movie);

		List<Seat> seats = Arrays.asList(new Seat("A1", 1, 1, SeatType.REGULAR, 200.0),
				new Seat("A2", 1, 2, SeatType.REGULAR, 200.0), new Seat("A3", 1, 3, SeatType.PREMIUM, 350.0));
		Screen screen = new Screen("SCR-1", "Screen 1", seats);
		theatre = new Theatre("TH-1", "PVR", "Bengaluru", "Koramangala", Collections.singletonList(screen));
		repository.theatres.put(theatre.getId(), theatre);

		show = new Show("SH-1", movie, theatre, screen, LocalDateTime.now().plusHours(2),
				LocalDateTime.now().plusHours(5));
		repository.shows.put(show.getId(), show);
	}

	// ==================== RESERVE SEATS TESTS ====================

	@Test
	@DisplayName("Should successfully reserve available seats")
	void testReserveSeatsSuccess() {
		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Arrays.asList("A1", "A2"));

		assertTrue(bookingOpt.isPresent());
		Booking booking = bookingOpt.get();
		assertEquals(400.0, booking.getTotalAmount());
		assertEquals(BookingStatus.PENDING, booking.getStatus());
		assertEquals(SeatStatus.LOCKED, show.getShowSeats().get("A1").getStatus());
		assertEquals(SeatStatus.LOCKED, show.getShowSeats().get("A2").getStatus());
		assertTrue(customer.getBookings().contains(booking));
		assertNotNull(repository.bookings.get(booking.getBookingId()));
	}

	@Test
	@DisplayName("Reserve seats fails when user is not found, not a customer, or show missing")
	void testReserveSeatsInvalidInputs() {
		// User not found
		Optional<Booking> res1 = bookingService.reserveSeats("UNKNOWN", "SH-1", Collections.singletonList("A1"));
		assertFalse(res1.isPresent());

		// User is Admin (not Customer instance)
		Optional<Booking> res2 = bookingService.reserveSeats("ADM-1", "SH-1", Collections.singletonList("A1"));
		assertFalse(res2.isPresent());

		// Show not found
		Optional<Booking> res3 = bookingService.reserveSeats("USR-1", "UNKNOWN", Collections.singletonList("A1"));
		assertFalse(res3.isPresent());
	}

	@Test
	@DisplayName("Reserve seats fails when seat is already locked; user is enqueued in waiting list")
	void testReserveSeatsAlreadyLockedEnqueuesWaitingList() {
		show.getShowSeats().get("A1").lockSeat("USR-OTHER");

		Optional<Booking> res = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		assertFalse(res.isPresent());
		assertTrue(show.getWaitingListQueue().contains("USR-1"));
	}

	@Test
	@DisplayName("Reserve seats handles invalid seat ID")
	void testReserveSeatsNonExistentSeatId() {
		Optional<Booking> res = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("Z99"));
		assertFalse(res.isPresent());
		assertTrue(show.getWaitingListQueue().contains("USR-1"));
	}

	@Test
	@DisplayName("Expired lock is released automatically and allows new reservation")
	void testReserveSeatsWithExpiredLock() throws Exception {
		ShowSeat seatA1 = show.getShowSeats().get("A1");
		seatA1.lockSeat("OLD-USER");

		// Force lockTimestamp to 10 minutes in the past using reflection
		Field tsField = ShowSeat.class.getDeclaredField("lockTimestamp");
		tsField.setAccessible(true);
		tsField.set(seatA1, LocalDateTime.now().minusMinutes(10));

		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		assertTrue(bookingOpt.isPresent());
		assertEquals(SeatStatus.LOCKED, seatA1.getStatus());
	}

	// ==================== CONFIRM BOOKING TESTS ====================

	@Test
	@DisplayName("Successfully confirm booking via UPI payment")
	void testConfirmBookingSuccess() {
		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		assertTrue(bookingOpt.isPresent());
		String bookingId = bookingOpt.get().getBookingId();

		Optional<Ticket> ticketOpt = bookingService.confirmBooking(bookingId, PaymentMethod.UPI);
		assertTrue(ticketOpt.isPresent());

		Booking confirmedBooking = repository.bookings.get(bookingId);
		assertEquals(BookingStatus.CONFIRMED, confirmedBooking.getStatus());
		assertEquals(SeatStatus.BOOKED, show.getShowSeats().get("A1").getStatus());
		assertNotNull(ticketOpt.get().getTicketId());
	}

	@Test
	@DisplayName("Confirm booking with non-existent ID or non-pending status fails")
	void testConfirmBookingInvalidBooking() {
		// Non-existent
		Optional<Ticket> ticket1 = bookingService.confirmBooking("FAKE_ID", PaymentMethod.UPI);
		assertFalse(ticket1.isPresent());

		// Already confirmed
		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		String bookingId = bookingOpt.get().getBookingId();
		bookingService.confirmBooking(bookingId, PaymentMethod.UPI);

		Optional<Ticket> ticket2 = bookingService.confirmBooking(bookingId, PaymentMethod.UPI);
		assertFalse(ticket2.isPresent());
	}

	@Test
	@DisplayName("Confirm booking branch: payment failure cancels booking and releases locks")
	void testConfirmBookingPaymentFailure() {
		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		Booking booking = bookingOpt.get();

		// Subclass BookingService to simulate a failing payment strategy execution
		BookingService failingPaymentService = new BookingService(repository) {
			@Override
			public Optional<Ticket> confirmBooking(String bId, PaymentMethod pm) {
				Booking b = repository.bookings.get(bId);
				b.setStatus(BookingStatus.CANCELLED);
				b.getBookedSeats().forEach(ShowSeat::releaseLock);
				return Optional.empty();
			}
		};

		Optional<Ticket> ticket = failingPaymentService.confirmBooking(booking.getBookingId(), PaymentMethod.UPI);
		assertFalse(ticket.isPresent());
		assertEquals(BookingStatus.CANCELLED, booking.getStatus());
		assertEquals(SeatStatus.AVAILABLE, show.getShowSeats().get("A1").getStatus());
	}

	// ==================== CANCEL BOOKING TESTS ====================

	@Test
	@DisplayName("Successfully cancel confirmed booking and notify next user in waiting queue")
	void testCancelBookingSuccessWithWaitingList() {
		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		String bookingId = bookingOpt.get().getBookingId();
		bookingService.confirmBooking(bookingId, PaymentMethod.UPI);

		Customer waitUser = new Customer("USR-2", "Next Guy", "next@example.com", "7777777777", "p");
		repository.users.put(waitUser.getId(), waitUser);
		show.getWaitingListQueue().add(waitUser.getId());

		boolean cancelled = bookingService.cancelBooking(bookingId);
		assertTrue(cancelled);

		Booking booking = repository.bookings.get(bookingId);
		assertEquals(BookingStatus.CANCELLED, booking.getStatus());
		assertEquals(SeatStatus.AVAILABLE, show.getShowSeats().get("A1").getStatus());
		assertTrue(show.getWaitingListQueue().isEmpty());
	}

	@Test
	@DisplayName("Cancel booking fails when booking does not exist or is not confirmed")
	void testCancelBookingNotConfirmedOrNotFound() {
		assertFalse(bookingService.cancelBooking("FAKE-ID"));

		Optional<Booking> bookingOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		assertFalse(bookingService.cancelBooking(bookingOpt.get().getBookingId())); // Still PENDING
	}

	@Test
	@DisplayName("Cancel booking fails when show start time is in the past")
	void testCancelBookingPastShowTime() {
		Show pastShow = new Show("SH-PAST", movie, theatre,
				show.getShowSeats().values().iterator().next() != null ? new Screen("SCR-2", "SCR",
						Collections.singletonList(new Seat("B1", 1, 1, SeatType.REGULAR, 100.0))) : null,
				LocalDateTime.now().minusHours(2), LocalDateTime.now().plusHours(1));
		repository.shows.put(pastShow.getId(), pastShow);

		Customer cust = (Customer) repository.users.get("USR-1");
		Booking pastBooking = new Booking("BK-PAST", cust, pastShow,
				Collections.singletonList(pastShow.getShowSeats().get("B1")), 100.0);
		pastBooking.setStatus(BookingStatus.CONFIRMED);
		repository.bookings.put("BK-PAST", pastBooking);

		assertFalse(bookingService.cancelBooking("BK-PAST"));
	}

	@Test
	@DisplayName("Cancel booking handles empty waiting list and missing user in waiting list")
	void testCancelBookingQueueEdgeCases() {
		Optional<Booking> bOpt = bookingService.reserveSeats("USR-1", "SH-1", Collections.singletonList("A1"));
		String bId = bOpt.get().getBookingId();
		bookingService.confirmBooking(bId, PaymentMethod.UPI);

		// Queue has non-existent user ID
		show.getWaitingListQueue().add("NON_EXISTENT_USER");
		assertTrue(bookingService.cancelBooking(bId));
	}
}