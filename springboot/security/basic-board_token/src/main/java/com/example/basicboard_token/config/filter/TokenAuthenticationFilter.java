package com.example.basicboard_token.config.filter;

import com.example.basicboard_token.config.jwt.TokenProvider;
import com.example.basicboard_token.config.jwt.TokenStatus;
import com.example.basicboard_token.domain.entity.Member;
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
                Member member = tokenProvider.getTokenDetails(token);
                Authentication authentication = tokenProvider.getAuthentication(member, token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else if(status == TokenStatus.EXPIRED) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
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
