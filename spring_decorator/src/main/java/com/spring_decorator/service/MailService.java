package com.spring_decorator.service;

import com.spring_decorator.sender.NotificationSender;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final NotificationSender notificationSender;
    public MailService(NotificationSender notificationSender) {
        this.notificationSender = notificationSender;

    }

}
