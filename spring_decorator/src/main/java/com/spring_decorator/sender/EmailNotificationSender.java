package com.spring_decorator.sender;

import com.spring_decorator.dto.MailDto;

public class EmailNotificationSender implements NotificationSender {
    @Override
    public void send(MailDto mail) {
        System.out.printf("[EMAIL] from: %s, to: %s, subject: %s", mail.from(), mail.to(), mail.subject());
    }
}
