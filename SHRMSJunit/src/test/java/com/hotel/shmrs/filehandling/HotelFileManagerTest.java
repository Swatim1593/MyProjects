package com.hotel.shmrs.filehandling;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.hotel.shmrs.builder.BookingBuilder;
import com.hotel.shmrs.factory.RoomFactory;
import com.hotel.shmrs.model.entity.Booking;
import com.hotel.shmrs.model.entity.Customer;
import com.hotel.shmrs.model.entity.HotelService;
import com.hotel.shmrs.model.entity.Passport;
import com.hotel.shmrs.model.entity.Room;
import com.hotel.shmrs.model.enums.BookingStatus;
import com.hotel.shmrs.model.enums.PaymentMode;
import com.hotel.shmrs.model.enums.RoomType;

class HotelFileManagerTest {

	@TempDir
	Path tempDir;
	

	private Booking sampleBooking;

	@BeforeEach
	void setUp() {
		Customer customer = new Customer("C-101", "Karthik Subramanian", "karthik@example.com", true,
				new Passport("M7823901", "IND"), "PIN_8899");
		customer.addService(new HotelService("Luxury Airport Cab", 1800.0));
		Room room = RoomFactory.createRoom(RoomType.DELUXE, 101);

		sampleBooking = new BookingBuilder().setBookingId("BK-9003").setCustomer(customer).setRoom(room).setNights(2)
				.setTotalCost(9150.0).setPaymentMode(PaymentMode.UPI).setStatus(BookingStatus.CONFIRMED).build();
	}

	@Test
	@DisplayName("Should generate formatted folio invoice text file")
	void testGenerateFolioReceipt() throws IOException {
		Path receiptPath = tempDir.resolve("C://Users//conne//Documents//workspace-spring-tools-for-eclipse-5.1.1.RELEASE//SHRMSJunit//target/hotel_folio_BK9003.txt");
		HotelFileManager.generateFolioReceipt(receiptPath.toString(), sampleBooking);

		assertTrue(Files.exists(receiptPath));
		List<String> lines = Files.readAllLines(receiptPath);

		assertAll("Receipt Content Assertions",
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Taj Luxury Residences (Bengaluru)"))),
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Booking ID   : BK-9003"))),
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Guest Name   : Karthik Subramanian"))),
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Room Assigned: #101 [Deluxe Room]"))),
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Stay Length  : 2 Nights"))),
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Payment Mode : UPI"))),
				() -> assertTrue(lines.stream().anyMatch(l -> l.contains("Grand Total  : ₹9150.0"))));
	}

	@Test
	@DisplayName("Should serialize and deserialize booking object snapshot")
	void testBookingObjectSnapshot() throws IOException, ClassNotFoundException {
		Path snapshotPath = tempDir.resolve("booking_state.ser");
		HotelFileManager.exportBookingSnapshot(snapshotPath.toString(), sampleBooking);

		assertTrue(Files.exists(snapshotPath));

		Booking restored = HotelFileManager.importBookingSnapshot(snapshotPath.toString());

		assertNotNull(restored);
		assertEquals(sampleBooking.getBookingId(), restored.getBookingId());
		assertEquals(sampleBooking.getCustomer().getName(), restored.getCustomer().getName());
		assertEquals(sampleBooking.getRoom().getRoomNumber(), restored.getRoom().getRoomNumber());
		assertEquals(sampleBooking.getTotalCost(), restored.getTotalCost(), 0.001);
	}

	@Test
	@DisplayName("Should initialize and perform random access binary updates on ledger")
	void testRandomAccessLedgerOperations() throws IOException {
		Path ledgerPath = tempDir.resolve("room_ledger.dat");
		HotelFileManager.initializeLedgerRecord(ledgerPath.toString(), 101, false, 5000.0);
		assertFalse(HotelFileManager.readLedgerOccupancyDirect(ledgerPath.toString(), 0));

		HotelFileManager.updateLedgerOccupancyDirect(ledgerPath.toString(), 0, true);

		assertTrue(HotelFileManager.readLedgerOccupancyDirect(ledgerPath.toString(), 0));

	}

}