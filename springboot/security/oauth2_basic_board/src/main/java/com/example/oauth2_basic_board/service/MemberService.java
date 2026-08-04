package com.example.oauth2_basic_board.service;

import com.example.oauth2_basic_board.config.security.CustomUserDetails;
import com.example.oauth2_basic_board.domain.entity.Member;
import com.example.oauth2_basic_board.domain.entity.Role;
import com.example.oauth2_basic_board.domain.repository.MemberRepository;
import com.example.oauth2_basic_board.dto.request.LoginRequest;
import com.example.oauth2_basic_board.dto.request.MemberJoinRequest;
import com.example.oauth2_basic_board.dto.request.MemberRoleUpdateRequest;
import com.example.oauth2_basic_board.dto.response.LoginResponse;
import com.example.oauth2_basic_board.dto.response.MemberResponse;
import com.example.oauth2_basic_board.exception.DuplicateUserIdException;
import com.example.oauth2_basic_board.mapper.MemberMapper;
import com.example.oauth2_basic_board.service.component.TokenIssuer;
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
