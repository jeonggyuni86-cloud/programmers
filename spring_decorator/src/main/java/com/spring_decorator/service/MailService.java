package com.spring_decorator.service;
import com.spring_decorator.dto.MailDto;
import com.spring_decorator.logger.FileLogger;
import com.spring_decorator.logger.LogType;
import com.spring_decorator.sender.NotificationSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private final NotificationSender notificationSender;
    private final FileLogger logger;

    public MailService(NotificationSender notificationSender, FileLogger fileLogger) {
        this.notificationSender = notificationSender;
        this.logger = fileLogger;
    }

    public void sendMail(MailDto mail) {
        logger.log(LogType.INFO, "메일 서비스 요청 수신");
        try {
            notificationSender.send(mail);
            logger.log(LogType.INFO, "메일 서비스 처리 완료");
        } catch (RuntimeException e) {
            logger.log(LogType.ERROR, "메일 서비스 처리 실패: " + e.getMessage());
            throw e;
        }
    }



}
