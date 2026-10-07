package com.dispatch.state;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dispatch.enums.DriverStatus;
import com.dispatch.enums.RideType;
import com.dispatch.model.Driver;
import com.dispatch.model.Location;
import com.dispatch.model.Ride;
import com.dispatch.model.Rider;

class RideStateTransitionTest {

	private Rider rider;
	private Driver driver;
	private Location pickup;
	private Location dropoff;
	private Ride ride;

	@BeforeEach
	void setUp() {
		pickup = new Location(12.9350, 77.6240);
		dropoff = new Location(12.9716, 77.5946);
		rider = new Rider("USR-1", "Aakash Verma", pickup);
		rider.setActiveRide(true);
		driver = new Driver("DRV-1", "Kishore Kumar", pickup);
		ride = new Ride("TRIP-99", rider, pickup, dropoff, RideType.SEDAN);
		ride.setFare(500.0);
	}

	@Test
	@DisplayName("Initial state should be RequestedState")
	void testInitialState() {
		assertInstanceOf(RequestedState.class, ride.getState());
	}

	@Test
	@DisplayName("proceedToMatch should assign driver and transition to MatchedState")
	void testMatchTransition() {
		ride.proceedToMatch(driver);

		assertAll("Match Verification", () -> assertInstanceOf(MatchedState.class, ride.getState()),
				() -> assertEquals(driver, ride.getDriver()),
				() -> assertEquals(DriverStatus.MATCHED, driver.getStatus()));
	}

	@Test
	@DisplayName("startTrip should set driver to IN_TRANSIT and transition to TransitState")
	void testStartTripTransition() {
		ride.proceedToMatch(driver);
		ride.startTrip();

		assertInstanceOf(TransitState.class, ride.getState());
		assertEquals(DriverStatus.IN_TRANSIT, driver.getStatus());
	}

	@Test
	@DisplayName("completeTrip should calculate 80% payout, record history, and transition to CompletedState")
	void testCompleteTripTransition() {
		ride.proceedToMatch(driver);
		ride.startTrip();
		ride.completeTrip();

		assertAll("Completion Verification", () -> assertInstanceOf(CompletedState.class, ride.getState()),
				() -> assertEquals(400.0, driver.getEarnings(), 0.001),
				() -> assertEquals(DriverStatus.AVAILABLE, driver.getStatus()),
				() -> assertEquals(dropoff, driver.getLocation()), () -> assertEquals(dropoff, rider.getLocation()),
				() -> assertFalse(rider.hasActiveRide()), () -> assertTrue(rider.getTripHistory().contains(ride)),
				() -> assertTrue(driver.getCompletedRides().contains(ride)));
	}

	@Test
	@DisplayName("cancelTrip from RequestedState should transition to CancelledState and release rider active flag")
	void testCancelFromRequestedState() {
		ride.cancelTrip();

		assertInstanceOf(CancelledState.class, ride.getState());
		assertFalse(rider.hasActiveRide());
	}

	@Test
	@DisplayName("cancelTrip from MatchedState should release driver to AVAILABLE and cancel trip")
	void testCancelFromMatchedState() {
		ride.proceedToMatch(driver);
		ride.cancelTrip();

		assertInstanceOf(CancelledState.class, ride.getState());
		assertEquals(DriverStatus.AVAILABLE, driver.getStatus());
		assertFalse(rider.hasActiveRide());
	}

	@Test
	@DisplayName("Illegal transitions should not alter the state machine")
	void testIllegalStateTransitions() {
		// Cannot complete or start before matching
		ride.completeTrip();
		assertInstanceOf(RequestedState.class, ride.getState());

		ride.startTrip();
		assertInstanceOf(RequestedState.class, ride.getState());
	}
}