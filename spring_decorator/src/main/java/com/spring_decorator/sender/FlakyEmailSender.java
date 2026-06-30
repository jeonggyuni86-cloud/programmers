package com.spring_decorator.sender;

import com.spring_decorator.dto.MailDto;

public class FlakyEmailSender implements NotificationSender {
    private int attempt = 0;

    @Override
    public void send(MailDto mail) {
        attempt++;
        if(attempt < 3) {
            throw new RuntimeException("일시적 네트워크 오류 (시도: " + attempt + ")");
        }
        System.out.printf("[EMAIL] (시도 %d 성공) from: %s, to: %s, subject: %s", attempt, mail.from(), mail.to(), mail.subject());
    }
}
