package com.movieticket.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.ReentrantLock;

public class Show {
    private final String id;
    private final Movie movie;
    private final Theatre theatre;
    private final Screen screen;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private final Map<String, ShowSeat> showSeats;
    private final ConcurrentLinkedQueue<String> waitingListQueue;
    private final ReentrantLock showLock;

    public Show(String id, Movie movie, Theatre theatre, Screen screen, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = id;
        this.movie = movie;
        this.theatre = theatre;
        this.screen = screen;
        this.startTime = startTime;
        this.endTime = endTime;
        this.waitingListQueue = new ConcurrentLinkedQueue<>();
        this.showLock = new ReentrantLock();

        this.showSeats = new ConcurrentHashMap<>();
        screen.getSeats().forEach((seatId, seat) ->
            this.showSeats.put(seatId, new ShowSeat(seat.getSeatId(), seat.getSeatType(), seat.getPrice()))
        );
    }

    public String getId() { return id; }
    public Movie getMovie() { return movie; }
    public Theatre getTheatre() { return theatre; }
    public LocalDateTime getStartTime() { return startTime; }
    public Map<String, ShowSeat> getShowSeats() { return showSeats; }
    public ConcurrentLinkedQueue<String> getWaitingListQueue() { return waitingListQueue; }
    public ReentrantLock getShowLock() { return showLock; }
}