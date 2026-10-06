package com.hospital.observer;

public class SmsNotificationService implements NotificationObserver {
    @Override
    public void update(String message) {
        System.out.println("  [SMS ALERT] Dispatching SMS: " + message);
    }
}