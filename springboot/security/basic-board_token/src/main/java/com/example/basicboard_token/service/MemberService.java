package com.example.basicboard_token.service;

import com.example.basicboard_token.config.security.CustomUserDetails;
import com.example.basicboard_token.domain.entity.Member;
import com.example.basicboard_token.domain.entity.Role;
import com.example.basicboard_token.domain.repository.MemberRepository;
import com.example.basicboard_token.dto.request.MemberRoleUpdateRequest;
import com.example.basicboard_token.dto.request.LoginRequest;
import com.example.basicboard_token.dto.request.MemberJoinRequest;
import com.example.basicboard_token.dto.response.LoginResponse;
import com.example.basicboard_token.dto.response.MemberResponse;
import com.example.basicboard_token.exception.DuplicateUserIdException;
import com.example.basicboard_token.mapper.MemberMapper;
import com.example.basicboard_token.service.component.TokenIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenIssuer tokenComponent;

    @Transactional
    public void join(MemberJoinRequest request) {
        if(memberRepository.existsByUserId(request.userId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 ID 입니다.");
        }
        memberRepository.save(
                memberMapper.toEntity(
                        request,
                        passwordEncoder.encode(request.password())
                )
        );
    }

    public LoginResponse login(LoginRequest request) {
        var authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.userId(),
                        request.password()
                )
        );
        var user = ((CustomUserDetails) authenticate.getPrincipal()).getUser();
        var tokenPair = tokenComponent.issueToken(user);
        return LoginResponse.success(
                user.getUserId(),
                user.getUserName(),
                user.getRole(),
                tokenPair.accessToken(),
                tokenPair.refreshToken()
        );
    }

    public List<MemberResponse> getMembers() {
        return memberRepository.findAll().stream()
                .map(MemberResponse::from)
                .toList();
    }

    @Transactional
    public MemberResponse updateRole(long memberId, MemberRoleUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다."));

        if (request.role() == Role.ROLE_ADMIN) {
            member.promoteToAdmin();
        } else {
            member.demoteToUser();
        }
        return MemberResponse.from(member);
    }

}
