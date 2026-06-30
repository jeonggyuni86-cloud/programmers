package com.spring_decorator.factory;

import com.spring_decorator.logger.FileLogger;
import com.spring_decorator.sender.FlakyEmailSender;
import com.spring_decorator.sender.NotificationSender;
import com.spring_decorator.service.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SenderFactory {
    @Bean
    public NotificationSender notificationSender() {
        return new FlakyEmailSender();
    }
    @Bean
    public FileLogger fileLogger() {
        return new FileLogger();
    }
    @Bean
    public MailService mailService() {
        return new MailService(notificationSender(), fileLogger());
    }
}
