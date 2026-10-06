package com.hotel.shmrs.observer;

public class EmailNotification implements NotificationObserver {
    @Override
    public void notifyUser(String recipient, String message) {
        System.out.println("[Notification: EMAIL] -> " + recipient + " | " + message);
    }
}