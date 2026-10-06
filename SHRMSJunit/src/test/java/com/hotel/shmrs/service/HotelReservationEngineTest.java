package com.hotel.shmrs.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hotel.shmrs.exceptions.InvalidPaymentException;
import com.hotel.shmrs.exceptions.RoomNotAvailableException;
import com.hotel.shmrs.factory.RoomFactory;
import com.hotel.shmrs.model.entity.Booking;
import com.hotel.shmrs.model.entity.Customer;
import com.hotel.shmrs.model.entity.HotelService;
import com.hotel.shmrs.model.entity.Passport;
import com.hotel.shmrs.model.entity.Room;
import com.hotel.shmrs.model.enums.BookingStatus;
import com.hotel.shmrs.model.enums.PaymentMode;
import com.hotel.shmrs.model.enums.RoomType;
import com.hotel.shmrs.strategy.PaymentStrategy;

@ExtendWith(MockitoExtension.class)
class HotelReservationEngineTest {

	private HotelReservationEngine engine;
	private Customer regularCustomer;
	private Customer vipCustomer;
	private Room deluxeRoom;
	private Room premiumRoom;
	private Room suiteRoom;

	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;

	@Mock
	private PaymentStrategy mockPaymentStrategy;

	@BeforeEach
	void setUp() {
		System.setOut(new PrintStream(outputStream, true, StandardCharsets.UTF_8));

		engine = new HotelReservationEngine();

		regularCustomer = new Customer("C-101", "Arjun Verma", "arjun@example.com", false,
				new Passport("L982341", "IND"), "PIN_8899");
		vipCustomer = new Customer("C-102", "Priya Sharma", "priya@example.com", true, new Passport("K112233", "IND"),
				"PIN_4411");

		deluxeRoom = RoomFactory.createRoom(RoomType.DELUXE, 101);
		premiumRoom = RoomFactory.createRoom(RoomType.PREMIUM, 201);
		suiteRoom = RoomFactory.createRoom(RoomType.SUITE, 301);

		engine.registerRoom(deluxeRoom);
		engine.registerRoom(premiumRoom);
		engine.registerRoom(suiteRoom);
	}

	@AfterEach
	void tearDown() {
		System.setOut(originalOut);
	}

	@Nested
	@DisplayName("Inventory and Filtering Tests")
	class InventoryTests {

		@Test
		@DisplayName("Should sort available rooms ascending by base rate calculation")
		void testFilterAvailableRoomsSortedByPrice() {
			List<Room> available = engine.filterAvailableRoomsSortedByPrice();

			assertEquals(3, available.size());

			assertEquals(101, available.get(0).getRoomNumber());
			assertEquals(201, available.get(1).getRoomNumber());
			assertEquals(301, available.get(2).getRoomNumber());
		}

		@Test
		@DisplayName("Should filter out unavailable rooms from sorted list")
		void testFilterWithOccupiedRooms() {
			deluxeRoom.setAvailable(false);

			List<Room> available = engine.filterAvailableRoomsSortedByPrice();
			assertEquals(2, available.size());
			assertFalse(available.contains(deluxeRoom));
		}
	}

	@Nested
	@DisplayName("Booking Engine Tests")
	class BookingTests {

		@Test
		@DisplayName("Should successfully book room, charge payment, save in DAO, and notify observers")
		void testSuccessfulBooking() throws Exception {
			when(mockPaymentStrategy.getMode()).thenReturn(PaymentMode.UPI);
			when(mockPaymentStrategy.executePayment(anyDouble())).thenReturn(true);

			Booking booking = engine.bookRoom("BK-1001", regularCustomer, 101, 2, mockPaymentStrategy);

			assertAll("Booking Outcome", () -> assertNotNull(booking),
					() -> assertEquals("BK-1001", booking.getBookingId()), () -> assertFalse(deluxeRoom.isAvailable()),
					() -> assertEquals(BookingStatus.CONFIRMED, booking.getStatus()),
					() -> assertEquals(booking, engine.getDAO().findById("BK-1001")));

			verify(mockPaymentStrategy).executePayment(7350.0); // 3500 * 2 * 1.05
			String log = outputStream.toString(StandardCharsets.UTF_8);
			assertTrue(log.contains("[Notification: EMAIL]"));
			assertTrue(log.contains("[Notification: SMS]"));
		}

		@Test
		@DisplayName("Should include customer add-on services in grand total")
		void testBookingWithAddOnServices() throws Exception {
			regularCustomer.addService(new HotelService("Luxury Airport Cab", 1800.0));
			when(mockPaymentStrategy.getMode()).thenReturn(PaymentMode.CREDIT_CARD);
			when(mockPaymentStrategy.executePayment(anyDouble())).thenReturn(true);

			Booking booking = engine.bookRoom("BK-1002", regularCustomer, 101, 1, mockPaymentStrategy);

			assertEquals(5475.0, booking.getTotalCost(), 0.001);
			verify(mockPaymentStrategy).executePayment(5475.0);
		}

		@Test
		@DisplayName("Should throw IllegalArgumentException when booking non-registered room")
		void testInvalidRoomNumber() {
			IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
					() -> engine.bookRoom("BK-999", regularCustomer, 999, 1, mockPaymentStrategy));
			assertEquals("Invalid room number provided: #999", ex.getMessage());
		}

		@Test
		@DisplayName("Should throw RoomNotAvailableException if room is already occupied")
		void testOccupiedRoomThrowsException() {
			deluxeRoom.setAvailable(false);

			assertThrows(RoomNotAvailableException.class,
					() -> engine.bookRoom("BK-1003", regularCustomer, 101, 1, mockPaymentStrategy));
		}

		@Test
		@DisplayName("Should rollback room availability to true if payment execution fails")
		void testPaymentFailureRollsBackAvailability() throws Exception {
			when(mockPaymentStrategy.executePayment(anyDouble()))
					.thenThrow(new InvalidPaymentException("Payment value must be strictly positive."));

			assertThrows(InvalidPaymentException.class,
					() -> engine.bookRoom("BK-1004", regularCustomer, 101, 1, mockPaymentStrategy));

			assertTrue(deluxeRoom.isAvailable(), "Room should be rolled back to available");
			assertNull(engine.getDAO().findById("BK-1004"));
		}
	}

	@Nested
	@DisplayName("Cancellation Engine Tests")
	class CancellationTests {

		@Test
		@DisplayName("Should cancel confirmed booking, release room, update DAO, and notify observers")
		void testCancelBookingSuccess() throws Exception {
			when(mockPaymentStrategy.getMode()).thenReturn(PaymentMode.UPI);
			when(mockPaymentStrategy.executePayment(anyDouble())).thenReturn(true);

			engine.bookRoom("BK-1005", regularCustomer, 101, 1, mockPaymentStrategy);
			assertFalse(deluxeRoom.isAvailable());

			engine.cancelBooking("BK-1005");

			Booking cancelledBooking = engine.getDAO().findById("BK-1005");
			assertEquals(BookingStatus.CANCELLED, cancelledBooking.getStatus());
			assertTrue(deluxeRoom.isAvailable());

			String log = outputStream.toString(StandardCharsets.UTF_8);
			assertTrue(log.contains("[Cancellation Engine] Booking #BK-1005 cancelled."));
		}

		@Test
		@DisplayName("Should ignore cancellation for invalid booking ID without error")
		void testCancelNonExistentBooking() {
			assertDoesNotThrow(() -> engine.cancelBooking("UNKNOWN_ID"));
		}
	}

	@Nested
	@DisplayName("Front Desk Queue Tests")
	class QueueTests {

		@Test
		@DisplayName("Priority queue should prioritize VIP guests before standard guests")
		void testVIPQueuePriority() {
			engine.enqueueCustomer(regularCustomer); // Non-VIP
			engine.enqueueCustomer(vipCustomer); // VIP

			engine.processNextCheckIn();
			String log1 = outputStream.toString(StandardCharsets.UTF_8);
			assertTrue(log1.contains("Now serving guest: Priya Sharma (VIP: true)"));

			outputStream.reset();

			engine.processNextCheckIn();
			String log2 = outputStream.toString(StandardCharsets.UTF_8);
			assertTrue(log2.contains("Now serving guest: Arjun Verma (VIP: false)"));
		}

		@Test
		@DisplayName("Processing check-in on empty queue should perform no-op")
		void testEmptyQueueCheckIn() {
			assertDoesNotThrow(() -> engine.processNextCheckIn());
			assertEquals("", outputStream.toString(StandardCharsets.UTF_8).trim());
		}
	}
}