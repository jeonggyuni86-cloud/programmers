package org.example.webservice.service;

import lombok.RequiredArgsConstructor;
import org.example.webservice.client.AuthClient;
import org.example.webservice.dto.SignInRequestDto;
import org.example.webservice.dto.SignInResponseDto;
import org.example.webservice.dto.SignUpRequestDto;
import org.example.webservice.dto.SignUpResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {
        return authClient.join(requestDto);
    }
    public ResponseEntity<SignInResponseDto> signIn(SignInRequestDto signInRequestDto) {
        return authClient.login(signInRequestDto);
    }

}
