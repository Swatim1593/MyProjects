package com.hotel.shmrs.config;

public class HotelConfiguration {
    private static volatile HotelConfiguration instance;
    private final String hotelName = "Taj Luxury Residences";
    private final String city = "Bengaluru";

    private HotelConfiguration() {}

    public static HotelConfiguration getInstance() {
        if (instance == null) {
            synchronized (HotelConfiguration.class) {
                if (instance == null) {
                    instance = new HotelConfiguration();
                }
            }
        }
        return instance;
    }

    public String getPropertyDetails() {
        return hotelName + " (" + city + ")";
    }
}