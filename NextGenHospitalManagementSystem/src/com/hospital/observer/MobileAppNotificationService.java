package com.hospital.observer;

public class MobileAppNotificationService implements NotificationObserver {
    @Override
    public void update(String message) {
        System.out.println("  [MOBILE PUSH] Dispatching App Notification: " + message);
    }
}