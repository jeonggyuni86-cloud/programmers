package org.example.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.authservice.config.oauth2.AuthProvider;

@Getter
@AllArgsConstructor
public class SignupPayloadDto {
    private final AuthProvider provider;
    private final String providerId; // SNS 회원번호 (토큰의 sub 클레임에서 복원)
    private final String email;
    private final String name;
}
