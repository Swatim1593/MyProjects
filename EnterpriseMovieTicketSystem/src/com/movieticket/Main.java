package com.movieticket;

import com.movieticket.enums.*;
import com.movieticket.facade.BookingFacade;
import com.movieticket.model.*;
import com.movieticket.repository.DataRepository;
import com.movieticket.service.BookingService;
import com.movieticket.service.CatalogService;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=========================================================");
        System.out.println("   ENTERPRISE MOVIE TICKET BOOKING SYSTEM (JAVA 8)       ");
        System.out.println("=========================================================\n");

        DataRepository repository = new DataRepository();
        BookingService bookingService = new BookingService(repository);
        CatalogService catalogService = new CatalogService(repository);
        BookingFacade bookingFacade = new BookingFacade(bookingService);

        Movie movie = new Movie.MovieBuilder("M-101", "KGF-3", "Kannada", Genre.ACTION)
                .duration(180)
                .rating(4.9)
                .director("Prashanth Neel")
                .build();
        repository.movies.put(movie.getId(), movie);

        List<Seat> screenSeats = Arrays.asList(
            new Seat("A1", 1, 1, SeatType.REGULAR, 200.0),
            new Seat("A2", 1, 2, SeatType.REGULAR, 200.0),
            new Seat("A3", 1, 3, SeatType.REGULAR, 350.0)
        );

        Screen screen = new Screen("SCR-1", "IMAX Screen 1", screenSeats);
        Theatre theatre = new Theatre("TH-1", "PVR Cinemas", "Bengaluru", "Koramangala", Collections.singletonList(screen));
        repository.theatres.put(theatre.getId(), theatre);

        Show show = new Show("SH-501", movie, theatre, screen, LocalDateTime.now().plusHours(2), LocalDateTime.now().plusHours(5));
        repository.shows.put(show.getId(), show);

        int concurrentUserCount = 100;
        for (int i = 1; i <= concurrentUserCount; i++) {
            String userId = "USR-" + i;
            Customer customer = new Customer(userId, "Customer_" + i, "user" + i + "@example.com", "98765432" + i, "password");
            repository.users.put(userId, customer);
        }

        System.out.println("Target Show: " + show.getMovie().getTitle() + " at " + theatre.getName() + " (" + theatre.getCity() + ")");
        System.out.println("Simulating " + concurrentUserCount + " concurrent users attempting to lock Seat 'A1' simultaneously...\n");

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        CountDownLatch latch = new CountDownLatch(1);
        List<Future<Optional<Booking>>> futures = new ArrayList<>();

        for (int i = 1; i <= concurrentUserCount; i++) {
            final String userId = "USR-" + i;
            Callable<Optional<Booking>> task = () -> {
                latch.await();
                return bookingService.reserveSeats(userId, "SH-501", Collections.singletonList("A1"));
            };
            futures.add(executorService.submit(task));
        }

        latch.countDown();

        List<Booking> successfulLocks = new ArrayList<>();
        for (Future<Optional<Booking>> future : futures) {
            try {
                Optional<Booking> res = future.get();
                res.ifPresent(successfulLocks::add);
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);

        System.out.println("\n---------------- CONCURRENCY TEST SUMMARY ----------------");
        System.out.println("Total Concurrent Requests: " + concurrentUserCount);
        System.out.println("Successful Locks Granted: " + successfulLocks.size());
        System.out.println("Waiting List Queue Size: " + show.getWaitingListQueue().size());
        System.out.println("----------------------------------------------------------\n");

        if (!successfulLocks.isEmpty()) {
            Booking winningBooking = successfulLocks.get(0);
            String winningUserId = winningBooking.getCustomer().getId();

            System.out.println("Testing Facade + Decorator Workflow for Winning User [" + winningUserId + "]...");
            
            Optional<Ticket> ticketOpt = bookingFacade.bookMovieTicketWithAddons(
                    winningUserId,
                    "SH-501",
                    Collections.singletonList("A1"),
                    PaymentMethod.UPI,
                    true,  // Add Popcorn
                    true,  // Add 3D Glasses
                    false  // Add Insurance
            );

            ticketOpt.ifPresent(ticket -> System.out.println("\nFINAL ISSUED E-TICKET:\n" + ticket));
        }

        catalogService.generateRevenueAndOccupancyReport();

        if (!successfulLocks.isEmpty()) {
            System.out.println("Testing Cancellation & Priority Alert Pipeline...");
            String winningBookingId = successfulLocks.get(0).getBookingId();
            bookingService.cancelBooking(winningBookingId);
        }
    }
}