package com.hotel.shmrs.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.hotel.shmrs.factory.RoomFactory;
import com.hotel.shmrs.model.entity.DeluxeRoom;
import com.hotel.shmrs.model.entity.PremiumRoom;
import com.hotel.shmrs.model.entity.Room;
import com.hotel.shmrs.model.entity.SuiteRoom;
import com.hotel.shmrs.model.enums.RoomType;

class HotelConfigurationAndFactoryTest {
	

	@Nested
	@DisplayName("HotelConfiguration Singleton Tests")
	class ConfigurationTests {

		@Test
		@DisplayName("Should verify Singleton instance and property details ")
		void testSingletonInstance() {
			HotelConfiguration instance1 = HotelConfiguration.getInstance();
			HotelConfiguration instance2 = HotelConfiguration.getInstance();

			assertSame(instance1, instance2);
			assertEquals("Taj Luxury Residences (Bengaluru)", instance1.getPropertyDetails());

		}

		@Test
		@DisplayName("Should maintain singleton identity across concurrent threads")

		void testConcurrentSingletonAccess() throws InterruptedException {
			int threadCount = 10;
			ExecutorService executor = Executors.newFixedThreadPool(threadCount);
			CountDownLatch latch = new CountDownLatch(threadCount);
			HotelConfiguration[] instances = new HotelConfiguration[threadCount];

			for (int i = 0; i < threadCount; i++) {
				final int index = i;
				executor.submit(() -> {
					instances[index] = HotelConfiguration.getInstance();
					latch.countDown();
				});
			}

			latch.await();
			executor.shutdown();

			for (int i = 1; i < threadCount; i++) {
				assertSame(instances[0], instances[i]);
			}
		}

	}

	@Nested
	@DisplayName("Room Factory Tests")
	class RoomFactoryTests {

		@Test
		@DisplayName("Should create DeluxeRoom for DELUXE type")

		void testCreateDeluxeRoom() {
			Room room = RoomFactory.createRoom(RoomType.DELUXE, 101);
			assertInstanceOf(DeluxeRoom.class, room);
			assertEquals(RoomType.DELUXE, room.getRoomType());

		}

		@Test
		@DisplayName("Should create PremiumRoom for PREMIUM type")

		void testCreatePremiumRoom() {
			Room room = RoomFactory.createRoom(RoomType.PREMIUM, 201);
			assertInstanceOf(PremiumRoom.class, room);
			assertEquals(RoomType.PREMIUM, room.getRoomType());

		}

		@Test
		@DisplayName("Should create SuiteRoom for SUITE type")

		void testCreateSuiteRoom() {
			Room room = RoomFactory.createRoom(RoomType.SUITE, 301);
			assertInstanceOf(SuiteRoom.class, room);
			assertEquals(RoomType.SUITE, room.getRoomType());

		}
	}
}