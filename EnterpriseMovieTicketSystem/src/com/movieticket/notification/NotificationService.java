package com.movieticket.notification;

import com.movieticket.model.User;

public interface NotificationService {
    void sendNotification(User user, String message);
}