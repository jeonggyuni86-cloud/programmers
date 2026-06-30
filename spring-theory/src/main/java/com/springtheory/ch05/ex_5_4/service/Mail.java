package com.springtheory.ch05.ex_5_4.service;

import lombok.AllArgsConstructor;
import lombok.Getter;

//보낼 메일 한 통을 나타내는 값 객체
@Getter
@AllArgsConstructor
public class Mail {
    private String to;
    private String subject;
    private String text;
}
