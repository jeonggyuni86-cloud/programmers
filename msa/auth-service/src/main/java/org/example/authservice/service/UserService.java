package org.example.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.authservice.config.security.CustomUserDetails;
import org.example.authservice.domain.entity.User;
import org.example.authservice.domain.repository.UserRepository;
import org.example.authservice.dto.SignInRequestDto;
import org.example.authservice.dto.SignInResponseDto;
import org.example.authservice.dto.SignUpRequestDto;
import org.example.authservice.exception.DuplicateUserIdException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public void signUp(
            SignUpRequestDto request
    ) {

        if(userRepository.existsByUserId(request.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 사용중인 ID 입니다");
        }
        User user = request.touser(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    public SignInResponseDto login(SignInRequestDto signInRequestDto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDto.getUserId(), signInRequestDto.getPassword())
        );

        User user = ((CustomUserDetails) authenticate.getPrincipal()).getUser();
        TokenService.TokenPair tokenPair = tokenService.issueToken(user);

        return SignInResponseDto.builder()
                .loggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userId(user.getUserId())
                .userName(user.getName())
                .build();
    }

}
