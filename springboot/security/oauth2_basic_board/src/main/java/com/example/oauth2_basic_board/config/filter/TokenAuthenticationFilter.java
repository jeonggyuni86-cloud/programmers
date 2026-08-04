package com.example.oauth2_basic_board.config.filter;

import com.example.oauth2_basic_board.config.jwt.TokenProvider;
import com.example.oauth2_basic_board.config.jwt.TokenStatus;
import com.example.oauth2_basic_board.domain.entity.Member;
import com.example.oauth2_basic_board.domain.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberRepository memberRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return path.equals("/api/members/login")
                || path.equals("/api/members/join");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        String token = resolveToken(request);

        if(token != null) {
            TokenStatus status = tokenProvider.validateToken(token);
            if(status == TokenStatus.VALID) {
                Member tokenMember = tokenProvider.getTokenDetails(token);
                memberRepository.findById(tokenMember.getId()).ifPresent(member -> {
                    Authentication authentication = tokenProvider.getAuthentication(member, token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                });
            } else if(status == TokenStatus.EXPIRED) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write(
                        "{\"status\":401,\"message\":\"로그인이 만료되었습니다. 다시 로그인해 주세요.\"}"
                );
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
