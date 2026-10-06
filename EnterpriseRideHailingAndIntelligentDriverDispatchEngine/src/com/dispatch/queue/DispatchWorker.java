package com.dispatch.queue;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.dispatch.dao.DriverDaoImpl;
import com.dispatch.dao.RideDaoImpl;
import com.dispatch.dao.RiderDaoImpl;
import com.dispatch.decorator.BaseRideBill;
import com.dispatch.decorator.ChildSeatDecorator;
import com.dispatch.decorator.ExtraLuggageDecorator;
import com.dispatch.decorator.RideComponent;
import com.dispatch.enums.DriverStatus;
import com.dispatch.enums.PaymentStatus;
import com.dispatch.event.RideRequestEvent;
import com.dispatch.model.Driver;
import com.dispatch.model.Payment;
import com.dispatch.model.Ride;
import com.dispatch.model.Rider;
import com.dispatch.stretegy.PricingStrategy;

public class DispatchWorker implements Runnable {
    private final String workerId;
    private final BlockingQueue<RideRequestEvent> queue;
    private final RiderDaoImpl riderDao;
    private final DriverDaoImpl driverDao;
    private final RideDaoImpl rideDao;
    private final PricingStrategy pricingStrategy;
    private volatile boolean active = true;

    public DispatchWorker(String workerId, BlockingQueue<RideRequestEvent> queue,
                          RiderDaoImpl riderDao, DriverDaoImpl driverDao,
                          RideDaoImpl rideDao, PricingStrategy pricingStrategy) {
        this.workerId = workerId;
        this.queue = queue;
        this.riderDao = riderDao;
        this.driverDao = driverDao;
        this.rideDao = rideDao;
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public void run() {
        while (active || !queue.isEmpty()) {
            try {
                RideRequestEvent event = queue.poll(400, TimeUnit.MILLISECONDS);
                if (event != null) {
                    processEvent(event);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private void processEvent(RideRequestEvent event) {
        Optional<Rider> riderOpt = riderDao.findById(event.getRiderId());
        if (!riderOpt.isPresent()) return;
        Rider rider = riderOpt.get();

        Ride ride = new Ride(event.getRideId(), rider, event.getPickup(), event.getDropoff(), event.getRideType());
        rideDao.save(ride);

        // Java 8 Streams: Geofence Filter & Euclidean Distance Sort (< 5 KM Radius)
        List<Driver> candidates = driverDao.findAll().stream()
                .filter(d -> d.getStatus() == DriverStatus.AVAILABLE)
                .filter(d -> d.getLocation().distanceTo(event.getPickup()) <= 5.0)
                .sorted(Comparator.comparingDouble(d -> d.getLocation().distanceTo(event.getPickup())))
                .collect(Collectors.toList());

        // Observer Pattern Broadcast
        candidates.forEach(d -> d.onRideRequested(ride.getRideId(), event.getPickup()));

        boolean matched = false;

        for (Driver driver : candidates) {
            // Atomic non-blocking acquisition
            if (driver.tryAcquireLock()) {
                try {
                    if (driver.getStatus() == DriverStatus.AVAILABLE) {
                        double distanceKm = event.getPickup().distanceTo(event.getDropoff());
                        
                        // 1. Strategy Pattern Surge Computation
                        double baseSurgeFare = pricingStrategy.calculateFare(ride, distanceKm);
                        
                        // 2. Decorator Pattern Dynamic Add-ons
                        RideComponent bill = new BaseRideBill(ride.getRideId(), baseSurgeFare);
                        if (event.isNeedsChildSeat()) {
                            bill = new ChildSeatDecorator(bill);
                        }
                        if (event.isNeedsExtraLuggage()) {
                            bill = new ExtraLuggageDecorator(bill);
                        }

                        ride.setFare(bill.getCost());

                        // 3. One-to-One Payment Entity Association
                        Payment payment = new Payment("PAY-" + UUID.randomUUID().toString().substring(0, 6),
                                ride.getRideId(), bill.getCost(), event.getPaymentMode());
                        payment.setStatus(PaymentStatus.SUCCESSFUL);
                        ride.setPayment(payment);

                        // 4. State Pattern Transition
                        ride.proceedToMatch(driver);
                        matched = true;

                        System.out.println(" [DISPATCHED] " + workerId + " matched Ride " + ride.getRideId() 
                                + " with Driver " + driver.getName() 
                                + " | Bill: " + bill.getDescription()
                                + " | Total: Rs." + String.format("%.2f", bill.getCost()));
                        break;
                    }
                } finally {
                    driver.releaseLock();
                }
            }
        }

        if (!matched) {
            System.out.println(" [UNFULFILLED] " + workerId + " found no available drivers for " + rider.getName());
            ride.cancelTrip();
        }
    }

    public void stop() {
        this.active = false;
    }
}