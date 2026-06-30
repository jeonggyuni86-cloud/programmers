package com.spring_decorator.sender;

import com.spring_decorator.dto.MailDto;

public interface NotificationSender {
    void send(MailDto mail);
}
