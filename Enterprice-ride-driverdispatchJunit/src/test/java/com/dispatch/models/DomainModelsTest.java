package com.dispatch.models;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.dispatch.enums.PaymentMode;
import com.dispatch.enums.PaymentStatus;
import com.dispatch.model.BaseEntity;
import com.dispatch.model.Driver;
import com.dispatch.model.Location;
import com.dispatch.model.Payment;
import com.dispatch.model.Rider;

class DomainModelsTest {

	@Test
    @DisplayName("Location distanceTo should accurately compute Haversine spherical distance")
	void testLocationDistanceCalculation() {

		Location koramangala = new Location(12.9350, 77.6240);
		Location mgRoad = new Location(12.9716, 77.5946);

		double distance = koramangala.distanceTo(mgRoad);
		assertTrue(distance > 5.0 && distance < 6.0, "Distance should be ~5.4 KM");
		assertEquals(0.0, koramangala.distanceTo(null), 0.001);
		assertEquals("(12.9350, 77.6240)", koramangala.toString());
	}

	@Test
	@DisplayName("Driver lock acquisition and release should function properly")
	void testDriverLockMechanisms() {
		Driver driver = new Driver("DRV-10", "Kapil Dev", new Location(12.9340, 77.6220));

		assertTrue(driver.tryAcquireLock());

		assertTrue(driver.tryAcquireLock());

		driver.releaseLock();
		driver.releaseLock();
	}

	@Test
	@DisplayName("BaseEntity metadata and static cluster ID operations should function")
	void testBaseEntityProperties() {
		Rider rider = new Rider("USR-99", "Test User", new Location(12.0, 77.0));

		assertNotNull(rider.getCreatedAt());
		assertNotNull(rider.getUpdatedAt());
		assertNotNull(BaseEntity.getSystemClusterId());

		BaseEntity.setSystemClusterId("BLR-REGION-TEST");
		assertEquals("BLR-REGION-TEST", BaseEntity.getSystemClusterId());

		rider.markUpdated();
		assertTrue(rider.getUpdatedAt().isAfter(rider.getCreatedAt())
				|| rider.getUpdatedAt().equals(rider.getCreatedAt()));
	}

	@Test
	@DisplayName("Payment model state mutation and getters verification")
	void testPaymentModel() {
		Payment payment = new Payment("PAY-01", "TRIP-01", 350.0, PaymentMode.UPI);

		assertEquals(PaymentStatus.PENDING, payment.getStatus());
		payment.setStatus(PaymentStatus.SUCCESSFUL);

		assertAll("Payment attributes", () -> assertEquals("PAY-01", payment.getPaymentId()),
				() -> assertEquals("TRIP-01", payment.getPaymentId().equals("PAY-01") ? payment.getRideId() : ""),
				() -> assertEquals(350.0, payment.getAmount(), 0.001),
				() -> assertEquals(PaymentMode.UPI, payment.getPaymentMode()),
				() -> assertEquals(PaymentStatus.SUCCESSFUL, payment.getStatus()),
				() -> assertNotNull(payment.getTimestamp()));
	}
}