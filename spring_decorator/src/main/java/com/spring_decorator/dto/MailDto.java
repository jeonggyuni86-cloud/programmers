package com.spring_decorator.dto;

import lombok.Builder;

@Builder
public record MailDto(
        String from,
        String to,
        String subject,
        String content) {
}
