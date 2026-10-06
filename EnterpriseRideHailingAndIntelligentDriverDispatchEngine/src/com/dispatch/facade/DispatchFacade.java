package com.dispatch.facade;

import com.dispatch.dao.DriverDaoImpl;
import com.dispatch.dao.RideDaoImpl;
import com.dispatch.dao.RiderDaoImpl;
import com.dispatch.enums.PaymentMode;
import com.dispatch.enums.RideType;
import com.dispatch.event.RideRequestEvent;
import com.dispatch.model.Location;
import com.dispatch.model.Rider;
import com.dispatch.queue.DispatchWorker;
import com.dispatch.queue.RideRequestProducer;
import com.dispatch.state.CompletedState;
import com.dispatch.stretegy.PricingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

public class DispatchFacade {
    private final RiderDaoImpl riderDao;
    private final DriverDaoImpl driverDao;
    private final RideDaoImpl rideDao;
    private final BlockingQueue<RideRequestEvent> queue;
    private final RideRequestProducer producer;
    private final List<DispatchWorker> workers;
    private final ExecutorService workerExecutor;

    public DispatchFacade(RiderDaoImpl riderDao, DriverDaoImpl driverDao, RideDaoImpl rideDao,
                          PricingStrategy pricingStrategy, int workerPoolSize) {
        this.riderDao = riderDao;
        this.driverDao = driverDao;
        this.rideDao = rideDao;
        this.queue = new ArrayBlockingQueue<>(500); // Bounded queue buffer
        this.producer = new RideRequestProducer(queue);
        this.workers = new ArrayList<>();
        this.workerExecutor = Executors.newFixedThreadPool(workerPoolSize);

        for (int i = 1; i <= workerPoolSize; i++) {
            DispatchWorker worker = new DispatchWorker("Worker-" + i, queue, riderDao, driverDao, rideDao, pricingStrategy);
            workers.add(worker);
            workerExecutor.submit(worker);
        }
    }

    public String requestRide(String riderId, Location pickup, Location dropoff, 
                              RideType rideType, PaymentMode paymentMode, 
                              boolean childSeat, boolean luggage) {
        Optional<Rider> riderOpt = riderDao.findById(riderId);
        if (!riderOpt.isPresent()) {
            System.err.println("Invalid Rider ID.");
            return null;
        }

        Rider rider = riderOpt.get();
        if (rider.hasActiveRide()) {
            System.err.println("Rider " + rider.getName() + " already has an active trip.");
            return null;
        }

        String rideId = "TRIP-" + UUID.randomUUID().toString().substring(0, 8);
        RideRequestEvent event = new RideRequestEvent(rideId, riderId, pickup, dropoff, 
                                                      rideType, paymentMode, childSeat, luggage);
        rider.setActiveRide(true);
        producer.publishRequest(event);
        return rideId;
    }

    public void generateAdministrativeSummary() {
        System.out.println("\n==========================================================");
        System.out.println("          ADMINISTRATIVE REVENUE & METRICS AUDIT          ");
        System.out.println("==========================================================");

        long totalBookings = rideDao.findAll().size();
        long completedTrips = rideDao.findAll().stream()
                .filter(r -> r.getState() instanceof CompletedState)
                .count();

        double totalTurnover = rideDao.findAll().stream()
                .filter(r -> r.getState() instanceof CompletedState)
                .mapToDouble(r -> r.getFare())
                .sum();

        double platformCommission = totalTurnover * 0.20; // 20% Net Commission
        double totalDriverPayouts = driverDao.findAll().stream()
                .mapToDouble(d -> d.getEarnings())
                .sum();

        System.out.println("Total Trips Submitted   : " + totalBookings);
        System.out.println("Total Trips Completed   : " + completedTrips);
        System.out.println("Total Gross Billing     : Rs." + String.format("%.2f", totalTurnover));
        System.out.println("Net Company Commission  : Rs." + String.format("%.2f", platformCommission));
        System.out.println("Disbursed Driver Payout : Rs." + String.format("%.2f", totalDriverPayouts));
        System.out.println("==========================================================\n");
    }

    public void shutdown() throws InterruptedException {
        workers.forEach(DispatchWorker::stop);
        workerExecutor.shutdown();
        workerExecutor.awaitTermination(3, TimeUnit.SECONDS);
    }
}