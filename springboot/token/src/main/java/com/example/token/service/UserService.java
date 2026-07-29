package com.example.token.service;

import com.example.token.domain.entitiy.User;
import com.example.token.domain.repository.UserRepository;
import com.example.token.dto.SignUpRequest;
import com.example.token.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signUp(SignUpRequest request) {
        if(userRepository.existsByUserId(request.userId()))
            throw new DuplicateUserIdException("[회원가입] 이미 가입된 아이디 입니다.");
        User user = request.toUser(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

}
