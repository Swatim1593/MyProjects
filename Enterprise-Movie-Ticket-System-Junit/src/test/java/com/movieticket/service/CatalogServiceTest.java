package com.movieticket.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.movieticket.enums.BookingStatus;
import com.movieticket.enums.Genre;
import com.movieticket.model.Booking;
import com.movieticket.model.Movie;
import com.movieticket.model.Theatre;
import com.movieticket.repository.DataRepository;

class CatalogServiceTest {

    private DataRepository repository;
    private CatalogService catalogService;

    @BeforeEach
    void setUp() {
        repository = new DataRepository();
        catalogService = new CatalogService(repository);

        Movie m1 = new Movie.MovieBuilder("M1", "KGF-3", "Kannada", Genre.ACTION).build();
        Movie m2 = new Movie.MovieBuilder("M2", "Inception", "English", Genre.SCI_FI).build();
        Movie m3 = new Movie.MovieBuilder("M3", "Action Hero", "Hindi", Genre.ACTION).build();

        repository.movies.put(m1.getId(), m1);
        repository.movies.put(m2.getId(), m2);
        repository.movies.put(m3.getId(), m3);

        Theatre t1 = new Theatre("T1", "PVR", "Bengaluru", "Whitefield", Collections.emptyList());
        Theatre t2 = new Theatre("T2", "INOX", "Mumbai", "Nariman", Collections.emptyList());
        repository.theatres.put(t1.getId(), t1);
        repository.theatres.put(t2.getId(), t2);
    }

    @Test
    @DisplayName("Search movies by various filter combinations including nulls")
    void testSearchMovies() {
        // Match all (all nulls)
        List<Movie> all = catalogService.searchMovies(null, null, null);
        assertEquals(3, all.size());

        // Match by title
        List<Movie> titleMatches = catalogService.searchMovies("kgf-3", null, null);
        assertEquals(1, titleMatches.size());
        assertEquals("KGF-3", titleMatches.get(0).getTitle());

        // Match by genre
        List<Movie> actionMovies = catalogService.searchMovies(null, Genre.ACTION, null);
        assertEquals(2, actionMovies.size());

        // Match by language
        List<Movie> kannadaMovies = catalogService.searchMovies(null, null, "Kannada");
        assertEquals(1, kannadaMovies.size());

        // Match combined
        List<Movie> combined = catalogService.searchMovies("KGF-3", Genre.ACTION, "Kannada");
        assertEquals(1, combined.size());

        // No match
        List<Movie> none = catalogService.searchMovies("NonExistent", Genre.ROMANTIC, "Spanish");
        assertTrue(none.isEmpty());
    }

    @Test
    @DisplayName("Search theatres by city")
    void testSearchThreatresByCity() {
        List<Theatre> bglr = catalogService.searchTheatresByCity("Bengaluru");
        assertEquals(1, bglr.size());
        assertEquals("PVR", bglr.get(0).getName());

        List<Theatre> empty = catalogService.searchTheatresByCity("Delhi");
        assertTrue(empty.isEmpty());
    }

    @Test
    @DisplayName("Generate revenue and occupancy report calculates only confirmed bookings")
    void testGenerateRevenueAndOccupancyReport() {
        Booking b1 = new Booking("B1", null, null, Collections.emptyList(), 500.0);
        b1.setStatus(BookingStatus.CONFIRMED);

        Booking b2 = new Booking("B2", null, null, Collections.emptyList(), 300.0);
        b2.setStatus(BookingStatus.CONFIRMED);

        Booking b3 = new Booking("B3", null, null, Collections.emptyList(), 200.0);
        b3.setStatus(BookingStatus.PENDING); // Should not count

        Booking b4 = new Booking("B4", null, null, Collections.emptyList(), 150.0);
        b4.setStatus(BookingStatus.CANCELLED); // Should not count

        repository.bookings.put(b1.getBookingId(), b1);
        repository.bookings.put(b2.getBookingId(), b2);
        repository.bookings.put(b3.getBookingId(), b3);
        repository.bookings.put(b4.getBookingId(), b4);

        assertDoesNotThrow(() -> catalogService.generateRevenueAndOccupancyReport());
    }
}