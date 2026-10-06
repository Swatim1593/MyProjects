package com.dispatch.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.dispatch.dao.DriverDaoImpl;
import com.dispatch.dao.RideDaoImpl;
import com.dispatch.dao.RiderDaoImpl;
import com.dispatch.enums.DriverStatus;
import com.dispatch.enums.PaymentMode;
import com.dispatch.enums.PaymentStatus;
import com.dispatch.enums.RideType;
import com.dispatch.event.RideRequestEvent;
import com.dispatch.model.Driver;
import com.dispatch.model.Location;
import com.dispatch.model.Ride;
import com.dispatch.model.Rider;
import com.dispatch.state.CancelledState;
import com.dispatch.state.MatchedState;
import com.dispatch.stretegy.PricingStrategy;
import com.dispatch.stretegy.StandardPricingStrategy;

@ExtendWith(MockitoExtension.class)
class DispatchWorkerMockitoTest {

	private RiderDaoImpl riderDao;
	private DriverDaoImpl driverDao;
	private RideDaoImpl rideDao;
	private BlockingQueue<RideRequestEvent> queue;
	private PricingStrategy pricingStrategy;
	private DispatchWorker worker;

	private Location koramangalaPickup;
	private Location indiranagarDropoff;

	@BeforeEach
	void setUp() {
		riderDao = new RiderDaoImpl();
		driverDao = new DriverDaoImpl();
		rideDao = new RideDaoImpl();
		queue = new ArrayBlockingQueue<>(10);
		pricingStrategy = spy(new StandardPricingStrategy());

		worker = new DispatchWorker("Worker-Test-1", queue, riderDao, driverDao, rideDao, pricingStrategy);

		koramangalaPickup = new Location(12.9350, 77.6240);
		indiranagarDropoff = new Location(12.9716, 77.5946);
	}

	@Test
	@DisplayName("RideRequestProducer should successfully publish events to the shared queue")
	void testProducerPublish() {
		RideRequestProducer producer = new RideRequestProducer(queue);
		RideRequestEvent event = new RideRequestEvent("TRIP-1", "USR-1", koramangalaPickup, indiranagarDropoff,
				RideType.SEDAN, PaymentMode.UPI, false, false);

		assertTrue(producer.publishRequest(event));
		assertEquals(1, queue.size());
	}

	@Test
	@DisplayName("Should successfully match closest available driver, trigger observer alert, and attach payment")
	void testSuccessfulDriverMatchAndDispatch() {
		Rider rider = new Rider("USR-100", "Priya", koramangalaPickup);
		rider.setActiveRide(true);
		riderDao.save(rider);

		Driver nearbyDriver = spy(new Driver("DRV-1", "Kishore", new Location(12.9352, 77.6245)));
		Driver farDriver = spy(new Driver("DRV-2", "Distant Driver", new Location(13.0827, 80.2707)));

		driverDao.save(nearbyDriver);
		driverDao.save(farDriver);

		RideRequestEvent event = new RideRequestEvent("TRIP-501", "USR-100", koramangalaPickup, indiranagarDropoff,
				RideType.SEDAN, PaymentMode.UPI, true, false);

		queue.offer(event);
		worker.stop();
		worker.run(); 

		Ride savedRide = rideDao.findById("TRIP-501").orElse(null);
		assertNotNull(savedRide);
		assertInstanceOf(MatchedState.class, savedRide.getState());
		assertEquals(nearbyDriver, savedRide.getDriver());
		assertEquals(DriverStatus.MATCHED, nearbyDriver.getStatus());

		
		verify(nearbyDriver, times(1)).onRideRequested("TRIP-501", koramangalaPickup);
		verify(farDriver, never()).onRideRequested(any(), any());

		verify(pricingStrategy, times(1)).calculateFare(any(Ride.class), anyDouble());
		assertNotNull(savedRide.getPayment());
		assertEquals(PaymentStatus.SUCCESSFUL, savedRide.getPayment().getStatus());
	}

	@Test
	@DisplayName("Should cancel trip when no driver is within 5KM geofence")
	void testUnfulfilledRequestWhenNoDriverInRange() {
		Rider rider = new Rider("USR-200", "Rahul", koramangalaPickup);
		rider.setActiveRide(true);
		riderDao.save(rider);

		Driver outOfRangeDriver = new Driver("DRV-99", "Far Driver", new Location(13.0827, 80.2707));
		driverDao.save(outOfRangeDriver);

		RideRequestEvent event = new RideRequestEvent("TRIP-502", "USR-200", koramangalaPickup, indiranagarDropoff,
				RideType.AUTO, PaymentMode.CASH, false, false);

		queue.offer(event);
		worker.stop();
		worker.run();

		Ride savedRide = rideDao.findById("TRIP-502").orElse(null);
		assertNotNull(savedRide);
		assertInstanceOf(CancelledState.class, savedRide.getState());
		assertFalse(rider.hasActiveRide());
	}
}