package com.dispatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.dispatch.dao.DriverDaoImpl;
import com.dispatch.dao.RideDaoImpl;
import com.dispatch.dao.RiderDaoImpl;
import com.dispatch.enums.PaymentMode;
import com.dispatch.enums.RideType;
import com.dispatch.facade.DispatchFacade;
import com.dispatch.model.Driver;
import com.dispatch.model.Location;
import com.dispatch.model.Ride;
import com.dispatch.model.Rider;
import com.dispatch.stretegy.RainSurgePricingStrategy;

public class Main {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        System.out.println("=========================================================");
        System.out.println("   ENTERPRISE RIDE-HAILING DISPATCH ENGINE INITIALIZING  ");
        System.out.println("=========================================================\n");

        // 1. Initialize DAOs (Data Access Layer)
        RiderDaoImpl riderDao = new RiderDaoImpl();
        DriverDaoImpl driverDao = new DriverDaoImpl();
        RideDaoImpl rideDao = new RideDaoImpl();

        // 2. Populate Driver Registry in Cluster (Koramangala, Bengaluru)
        Driver d1 = new Driver("DRV-1", "Kishore Kumar", new Location(12.9352, 77.6245));
        Driver d2 = new Driver("DRV-2", "Sunil Gavaskar", new Location(12.9358, 77.6250));
        Driver d3 = new Driver("DRV-3", "Kapil Dev", new Location(12.9340, 77.6220));

        driverDao.save(d1);
        driverDao.save(d2);
        driverDao.save(d3);

        // 3. Initialize Facade with Rain Surge (1.8x) & 4 Dedicated Consumer Worker Threads
        DispatchFacade dispatchFacade = new DispatchFacade(riderDao, driverDao, rideDao, 
                                                           new RainSurgePricingStrategy(), 4);

        // 4. Seed 15 Concurrent Riders (Producers)
        int simulatedRiders = 15;
        for (int i = 1; i <= simulatedRiders; i++) {
            String riderId = "USR-" + i;
            Rider rider = new Rider(riderId, "Rider_" + i, new Location(12.9350, 77.6240));
            riderDao.save(rider);
        }

        System.out.println("System Cluster : " + Driver.getSystemClusterId());
        System.out.println("Drivers Active : " + driverDao.findAll().size());
        System.out.println("Simulating " + simulatedRiders + " concurrent requests for 3 available drivers...\n");

        // 5. High-Concurrency Stress Test (Latch Synchronized)
        ExecutorService producerPool = Executors.newFixedThreadPool(8);
        CountDownLatch starterGun = new CountDownLatch(1);
        List<Future<String>> futures = new ArrayList<>();

        Location pickupPoint = new Location(12.9350, 77.6240);
        Location dropoffPoint = new Location(12.9716, 77.5946); // ~8.5 KM Distance

        for (int i = 1; i <= simulatedRiders; i++) {
            final String rId = "USR-" + i;
            final boolean addSeat = (i % 2 == 0);
            final boolean addLuggage = (i % 3 == 0);

            Callable<String> task = () -> {
                starterGun.await(); // Synchronize release
                return dispatchFacade.requestRide(rId, pickupPoint, dropoffPoint, 
                                                 RideType.SEDAN, PaymentMode.UPI, 
                                                 addSeat, addLuggage);
            };
            futures.add(producerPool.submit(task));
        }

        starterGun.countDown(); // Fire simultaneous requests

        for (Future<String> f : futures) {
            f.get();
        }

        producerPool.shutdown();
        producerPool.awaitTermination(2, TimeUnit.SECONDS);

        // Allow consumer threads to finish matching
        Thread.sleep(1500);

        // 6. Simulate Trip Progressions (State Transitions)
        System.out.println("\n--- Simulating State Transitions (Start -> Complete) ---");
        for (Ride ride : rideDao.findAll()) {
            if (ride.getDriver() != null) {
                ride.startTrip();
                ride.completeTrip();
            }
        }

        // 7. Generate Administrative Revenue & Metrics Report
        dispatchFacade.generateAdministrativeSummary();

        // 8. Graceful Engine Termination
        dispatchFacade.shutdown();
        System.out.println("Engine shutdown complete. Zero resource leaks.");
    }
}