package com.movieticket.service;

import com.movieticket.enums.BookingStatus;
import com.movieticket.enums.Genre;
import com.movieticket.model.Booking;
import com.movieticket.model.Movie;
import com.movieticket.model.Theatre;
import com.movieticket.repository.DataRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CatalogService {
    private final DataRepository repository;

    public CatalogService(DataRepository repository) {
        this.repository = repository;
    }

    public List<Movie> searchMovies(String title, Genre genre, String language) {
        return repository.movies.values().stream()
                .filter(m -> title == null || m.getTitle().equalsIgnoreCase(title))
                .filter(m -> genre == null || m.getGenre() == genre)
                .filter(m -> language == null || m.getLanguage().equalsIgnoreCase(language))
                .collect(Collectors.toList());
    }

    public List<Theatre> searchTheatresByCity(String city) {
        return repository.theatres.values().stream()
                .filter(t -> t.getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());
    }

    public void generateRevenueAndOccupancyReport() {
        System.out.println("\n========================================================");
        System.out.println("        ADMINISTRATIVE REVENUE & OCCUPANCY REPORT       ");
        System.out.println("========================================================");

        double totalRevenue = repository.bookings.values().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .mapToDouble(Booking::getTotalAmount)
                .sum();

        long confirmedCount = repository.bookings.values().stream()
                .filter(b -> b.getStatus() == BookingStatus.CONFIRMED)
                .count();

        System.out.println("Total Confirmed Bookings: " + confirmedCount);
        System.out.println("Total System Revenue Realized: ₹" + totalRevenue);
        System.out.println("========================================================\n");
    }
}