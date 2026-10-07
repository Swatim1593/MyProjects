package com.dispatch.dao;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dispatch.enums.RideType;
import com.dispatch.model.Driver;
import com.dispatch.model.Location;
import com.dispatch.model.Ride;
import com.dispatch.model.Rider;

class DaoLayerTest {

	private DriverDaoImpl driverDao;
	private RiderDaoImpl riderDao;
	private RideDaoImpl rideDao;

	@BeforeEach
	void setUp() {
		driverDao = new DriverDaoImpl();
		riderDao = new RiderDaoImpl();
		rideDao = new RideDaoImpl();
	}

	@Test
	@DisplayName("DriverDao should perform save,findById,findAll,and delete operations")
	void testDriverDaoCrud() {
		Driver driver = new Driver("DRV-1", "Kishore Kumar", new Location(12.9352, 77.6245));
		driverDao.save(driver);

		Optional<Driver> found = driverDao.findById("DRV-1");
		assertTrue(found.isPresent());
		assertEquals("Kishore Kumar", found.get().getName());

		List<Driver> allDrivers = driverDao.findAll();
		assertEquals(1, allDrivers.size());

		driverDao.delete("DRV-1");
		assertFalse(driverDao.findById("DRV-1").isPresent());
		assertTrue(driverDao.findAll().isEmpty());

	}
	
	@Test
	@DisplayName("RiderDao should perform save,findById,findAll,and delete operations")
	void testRiderDaoCrud() {
		Rider rider = new Rider("USR-1", "Ravi Shankar", new Location(12.9350, 77.6240));
		riderDao.save(rider);

		Optional<Rider> found = riderDao.findById("USR-1");
		assertTrue(found.isPresent());
		assertEquals("Ravi Shankar", found.get().getName());

		List<Rider> allRiders = riderDao.findAll();
		assertEquals(1, allRiders.size());

		riderDao.delete("USR-1");
		assertFalse(riderDao.findById("USR-1").isPresent());
		assertTrue(riderDao.findAll().isEmpty());
	}
	@Test
	@DisplayName("RiderDao should perform save,findById,findAll,and delete operations")
	void testRideDaoCrud() {
		Rider rider = new Rider("USR-2", "Ananya", new Location(12.9350, 77.6240));
		Ride ride = new Ride("TRIP-101", rider,rider.getLocation(), new Location(12.9716, 77.5946),RideType.SEDAN);
		rideDao.save(ride);

		assertAll("RIdeDao verification",
				()->assertTrue(rideDao.findById("TRIP-101").isPresent()),
				()->assertEquals(ride,rideDao.findById("TRIP-101").get()),
				()->assertEquals(1,rideDao.findAll().size()));
	}
		

}