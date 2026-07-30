package com.example.token.service;

import com.example.token.config.security.CustomUserDetails;
import com.example.token.domain.entitiy.User;
import com.example.token.domain.repository.UserRepository;
import com.example.token.dto.SignInRequest;
import com.example.token.dto.SignInResponse;
import com.example.token.dto.SignUpRequest;
import com.example.token.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public void signUp(SignUpRequest request) {
        if(userRepository.existsByUserId(request.userId()))
            throw new DuplicateUserIdException("[회원가입] 이미 가입된 아이디 입니다.");
        User user = request.toUser(passwordEncoder.encode(request.password()));
        userRepository.save(user);
    }

    public SignInResponse login(SignInRequest request) {

        // form-login에서는 필터가 하던 아이디/비밀번호를 검증을 직접 호출한다.
        // 실패하면 AuthenticationException이 던져진다
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.userId(),
                        request.password()
                )
        );

        var user = ((CustomUserDetails) authenticate.getPrincipal()).getUser();;
        var tokenPair = tokenService.issueToken(user);

        return SignInResponse.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userId(user.getUserId())
                .build();
    }

}
