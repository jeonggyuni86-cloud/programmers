package com.spring_decorator.service;

import com.spring_decorator.dto.MailDto;
import com.spring_decorator.logger.FileLogger;
import com.spring_decorator.sender.NotificationSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MailServiceTest {

    @Test
    void 메일_발송_성공() {
        NotificationSender sender = mock(NotificationSender.class);
        FileLogger logger = mock(FileLogger.class);
        MailService mailService = new MailService(sender, logger);

        MailDto mail = MailDto.builder()
                .from("system@test.com")
                .to("abc@test.com")
                .subject("제목")
                .content("본문")
                .build();

        mailService.sendMail(mail);

        verify(sender).send(mail);
    }

    @Test
    void 메일_발송_실패시_예외를_던진다() {
        NotificationSender sender = mock(NotificationSender.class);
        FileLogger logger = mock(FileLogger.class);
        MailService mailService = new MailService(sender, logger);

        MailDto mail = MailDto.builder()
                .from("system@test.com")
                .to("abc@test.com")
                .subject("제목")
                .content("본문")
                .build();

        doThrow(new RuntimeException("SMTP 오류"))
                .when(sender)
                .send(mail);

        assertThrows(RuntimeException.class, () -> {
            mailService.sendMail(mail);
        });

        verify(sender).send(mail);
    }
}