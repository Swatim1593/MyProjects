package com.movieticket.model;

import com.movieticket.enums.Genre;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private final String id;
    private final String title;
    private final String language;
    private final Genre genre;
    private final int durationInMinutes;
    private final double rating;
    private final LocalDateTime releaseDate;
    private final String director;

    private Movie(MovieBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.language = builder.language;
        this.genre = builder.genre;
        this.durationInMinutes = builder.durationInMinutes;
        this.rating = builder.rating;
        this.releaseDate = builder.releaseDate;
        this.director = builder.director;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getLanguage() { return language; }
    public Genre getGenre() { return genre; }
    public double getRating() { return rating; }

    public static class MovieBuilder {
        private final String id;
        private final String title;
        private final String language;
        private final Genre genre;
        private int durationInMinutes = 120;
        private double rating = 0.0;
        private LocalDateTime releaseDate = LocalDateTime.now();
        private String director = "Unknown";

        public MovieBuilder(String id, String title, String language, Genre genre) {
            this.id = id;
            this.title = title;
            this.language = language;
            this.genre = genre;
        }

        public MovieBuilder duration(int durationInMinutes) {
            this.durationInMinutes = durationInMinutes;
            return this;
        }

        public MovieBuilder rating(double rating) {
            this.rating = rating;
            return this;
        }

        public MovieBuilder releaseDate(LocalDateTime releaseDate) {
            this.releaseDate = releaseDate;
            return this;
        }

        public MovieBuilder director(String director) {
            this.director = director;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}