package com.hotel.shrms.model.entity;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import com.hotel.shmrs.model.entity.DeluxeRoom;
import com.hotel.shmrs.model.entity.PremiumRoom;
import com.hotel.shmrs.model.entity.Room;
import com.hotel.shmrs.model.entity.SuiteRoom;
import com.hotel.shmrs.model.enums.RoomType;

class RoomTypesTest {

	@Nested
	@DisplayName("Room Instantiation and Attribute Tests")
	class RoomAttributeTests {

		@Test
		@DisplayName("Should correctly initialize DeluxeRoom attributes")

		void testDeluxeRoomInitialization() {

			Room deluxe = new DeluxeRoom(101);
			assertAll("DeluxeRoom properties", () -> assertEquals(101, deluxe.getRoomNumber()),
					() -> assertEquals(RoomType.DELUXE, deluxe.getRoomType()),
					() -> assertEquals("Deluxe Room", deluxe.getRoomType().getDisplayName()),
					() -> assertEquals(3500.0, deluxe.getRoomType().getBaseRate()),
					() -> assertEquals(0.05, deluxe.getRoomType().getTaxRate()),
					() -> assertTrue(deluxe.isAvailable()));

		}

		@Test
		@DisplayName("Should correctly initialize PremiumRoom attributes")

		void testPremiumRoomInitialization() {

			Room premium = new PremiumRoom(201);
			assertAll("PremiumRoom properties", () -> assertEquals(201, premium.getRoomNumber()),
					() -> assertEquals(RoomType.PREMIUM, premium.getRoomType()),
					() -> assertEquals("Premium Room", premium.getRoomType().getDisplayName()),
					() -> assertEquals(5500.0, premium.getRoomType().getBaseRate()),
					() -> assertEquals(0.10, premium.getRoomType().getTaxRate()),
					() -> assertTrue(premium.isAvailable()));

		}

		@Test
		@DisplayName("Should correctly initialize SuiteRoom attributes")

		void testSuiteRoomInitialization() {

			Room suite = new SuiteRoom(301);
			assertAll("SuiteRoom properties", () -> assertEquals(301, suite.getRoomNumber()),
					() -> assertEquals(RoomType.SUITE, suite.getRoomType()),
					() -> assertEquals("Executive Suite", suite.getRoomType().getDisplayName()),
					() -> assertEquals(9500.0, suite.getRoomType().getBaseRate()),
					() -> assertEquals(0.15, suite.getRoomType().getTaxRate()), () -> assertTrue(suite.isAvailable()));

		}

		@Test
		@DisplayName("Should update room availibility status")
		void testSetAvailable() {
			Room room = new DeluxeRoom(102);

			assertTrue(room.isAvailable());
			room.setAvailable(false);
			assertFalse(room.isAvailable());

			room.setAvailable(true);
			assertTrue(room.isAvailable());
		}

	}

	@Nested
	@DisplayName("Price Calculation Tests")
	class PriceCalculationTests {

		@ParameterizedTest(name = "Deluxe Room: {0} night(s) should cost ₹{1}")
		@CsvSource({ "1, 3675.0", "2, 7350.0", "5, 18375.0" })

		@DisplayName("Should calculate price accurately for DeluxeRoom")
		void testDeluxeRoomCalculatePrice(int nights, double expectedPrice) {
			Room room = new DeluxeRoom(101);
			assertEquals(expectedPrice, room.calculatePrice(nights), 0.001);
		}

		@ParameterizedTest(name = "Premium Room: {0} night(s) should cost ₹{1}")
		@CsvSource({ "1, 6050.0", "2, 12100.0", "3, 18150.0" })
		@DisplayName("Should calculate price accurately for PremiumRoom")
		void testPremiumRoomCalculatePrice(int nights, double expectedPrice) {
			Room room = new PremiumRoom(201);
			assertEquals(expectedPrice, room.calculatePrice(nights), 0.001);
		}

		@ParameterizedTest(name = "Suite Room: {0} night(s) should cost ₹{1} (includes ₹1000 surcharge)")
		@CsvSource({ "1, 11925.0", "2, 22850.0", "4, 44700.0" })

		@DisplayName("Should calculate price with ₹1000 butler surcharge for SuiteRoom")
		void testSuiteRoomCalculatePrice(int nights, double expectedPrice) {
			Room room = new SuiteRoom(301);
			assertEquals(expectedPrice, room.calculatePrice(nights), 0.001);

		}
	}

	@Nested
	@DisplayName("Comparison and String Representation Tests")
	class RoomContractTests {

		@Test
		@DisplayName("Should correctly compare rooms based on room number")
		void testCompareTo() {
			Room room101 = new DeluxeRoom(101);
			Room room201 = new PremiumRoom(201);
			Room roomSame101 = new SuiteRoom(101);

			assertTrue(room101.compareTo(room201) < 0);
			assertTrue(room201.compareTo(room101) > 0);
			assertEquals(0, room101.compareTo(roomSame101));
		}

		@Test
		@DisplayName("Should generate formatted toString representation")
		void testToStringFormatting() {
			Room deluxe = new DeluxeRoom(105);
			Room suite = new SuiteRoom(404);
			suite.setAvailable(false);

			assertEquals("[Deluxe Room #105 | Base: ₹3500.0 | Available=true]", deluxe.toString());
			assertEquals("[Executive Suite #404 | Base: ₹9500.0 | Available=false]", suite.toString());
		}
	}
}