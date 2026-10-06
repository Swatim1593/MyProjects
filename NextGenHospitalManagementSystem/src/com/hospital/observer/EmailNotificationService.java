package com.hospital.observer;

public class EmailNotificationService implements NotificationObserver {
    @Override
    public void update(String message) {
        System.out.println("  [EMAIL ALERT] Dispatching Email: " + message);
    }
}