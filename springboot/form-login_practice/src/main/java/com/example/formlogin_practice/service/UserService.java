package com.example.formlogin_practice.service;

import com.example.formlogin_practice.domain.repository.UserRepository;
import com.example.formlogin_practice.dto.SignUpRequest;
import com.example.formlogin_practice.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequest request) {
        if(userRepository.existsByUserId(request.userId())) {
            throw new DuplicateUserIdException("이미 사용중인 ID입니다.");
        }

        var user = request.toUser(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }
}
