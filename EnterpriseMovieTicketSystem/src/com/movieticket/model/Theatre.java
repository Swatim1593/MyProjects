package com.movieticket.model;

import java.util.List;

public class Theatre {
    private final String id;
    private final String name;
    private final String city;
    private final String location;
    private final List<Screen> screens;

    public Theatre(String id, String name, String city, String location, List<Screen> screens) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.location = location;
        this.screens = screens;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public String getLocation() { return location; }
    public List<Screen> getScreens() { return screens; }
}