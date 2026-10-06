package com.dispatch.decorator;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class RideDecoratorTest {

	@Test
	@DisplayName("BaseRideBill should calculate unadorned base fare")
	void testBaseRideBill() {
		RideComponent bill = new BaseRideBill("TRIP-001", 250.0);

		assertEquals(250.0, bill.getCost(), 0.001);
		assertEquals("Base Fare [TRIP-001]", bill.getDescription());
	}

	@Test
	@DisplayName("ChildSeatDecorator should increment fare by Rs.75 and append description")
	void testChildSeatDecorator() {
		RideComponent bill = new BaseRideBill("TRIP-002", 200.0);
		bill = new ChildSeatDecorator(bill);

		assertEquals(275.0, bill.getCost(), 0.001);
		assertTrue(bill.getDescription().contains("+Child Safety Seat(Rs.75)"));
	}

	@Test
	@DisplayName("ExtraLuggageDecorator should increment fare by Rs.50 and append description")
	void testExtraLuggageDecorator() {
		RideComponent bill = new BaseRideBill("TRIP-003", 200.0);
		bill = new ExtraLuggageDecorator(bill);

		assertEquals(250.0, bill.getCost(), 0.001);
		assertTrue(bill.getDescription().contains("+Extra Lugage Carrier(Rs.50)"));
	}

	@Test
	@DisplayName("Should dynamically chain multiple decorators together accurately")
	void testChainedDecorators() {
		RideComponent bill = new BaseRideBill("TRIP-004", 300.0);
		bill = new ChildSeatDecorator(bill);
		bill = new ExtraLuggageDecorator(bill);

		RideComponent finalBill = bill;
		assertAll("Chained Billing Verification", () -> assertEquals(425.0, finalBill.getCost(), 0.001),
				() -> assertTrue(finalBill.getDescription().contains("Base Fare [TRIP-004]")),
				() -> assertTrue(finalBill.getDescription().contains("+Child Safety Seat(Rs.75)")),
				() -> assertTrue(finalBill.getDescription().contains("+Extra Lugage Carrier(Rs.50)")));
	}
}