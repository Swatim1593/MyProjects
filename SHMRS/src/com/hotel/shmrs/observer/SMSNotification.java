package com.hotel.shmrs.observer;

public class SMSNotification implements NotificationObserver {
    @Override
    public void notifyUser(String recipient, String message) {
        System.out.println("[Notification: SMS]   -> " + recipient + " | " + message);
    }
}