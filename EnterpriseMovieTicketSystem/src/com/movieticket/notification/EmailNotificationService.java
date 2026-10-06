package com.movieticket.notification;

import com.movieticket.model.User;

public class EmailNotificationService implements NotificationService {
    @Override
    public void sendNotification(User user, String message) {
        System.out.println("[EMAIL -> " + user.getEmail() + "]: " + message);
    }
}