package com.spring_decorator.factory;

import com.spring_decorator.sender.FlakyEmailSender;
import com.spring_decorator.sender.NotificationSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderFactory {
    @Bean
    public NotificationSender notificationSender() {
        return new FlakyEmailSender();
    }
}
