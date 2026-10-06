package com.movieticket.notification;

import com.movieticket.model.User;

public class SMSNotificationService implements NotificationService {
    @Override
    public void sendNotification(User user, String message) {
        System.out.println("[SMS ALERT -> " + user.getPhone() + "]: " + message);
    }
}