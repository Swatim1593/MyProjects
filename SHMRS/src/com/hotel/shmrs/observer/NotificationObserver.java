package com.hotel.shmrs.observer;

public interface NotificationObserver {
    void notifyUser(String recipient, String message);
}