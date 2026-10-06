package com.movieticket.repository;

import com.movieticket.model.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DataRepository {
    public final Map<String, User> users = new ConcurrentHashMap<>();
    public final Map<String, Movie> movies = new ConcurrentHashMap<>();
    public final Map<String, Theatre> theatres = new ConcurrentHashMap<>();
    public final Map<String, Show> shows = new ConcurrentHashMap<>();
    public final Map<String, Booking> bookings = new ConcurrentHashMap<>();
}