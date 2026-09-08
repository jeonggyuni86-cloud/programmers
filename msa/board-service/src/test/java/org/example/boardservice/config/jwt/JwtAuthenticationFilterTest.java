package org.example.boardservice.config.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.example.boardservice.config.security.AuthenticatedUser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtAuthenticationFilterTest {

    private static final String SECRET =
            "dcQpTQijs+q6umfSzkr6y87Edlwc2fL3Cu0vCcYCjdjNURqityNidcTu2xHFZdqgdTkviIolP953zQKJfcatyA==";

    @AfterEach
    void clearSecurityContext() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void restoresAllUserClaimsFromAccessToken() throws Exception {
        JwtProperties properties = new JwtProperties();
        properties.setIssuer("test@naver.com");
        properties.setSecretKey(SECRET);

        JwtTokenProvider tokenProvider = new JwtTokenProvider(properties);
        tokenProvider.init();
        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(tokenProvider);

        SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
        String token = Jwts.builder()
                .issuer("test@naver.com")
                .subject("hong")
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .claim("id", 1L)
                .claim("name", "홍길동")
                .claim("role", "ROLE_USER")
                .signWith(key, Jwts.SIG.HS512)
                .compact();

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

        filter.doFilter(
                request,
                new MockHttpServletResponse(),
                new MockFilterChain()
        );

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull(authentication);
        AuthenticatedUser user = assertInstanceOf(
                AuthenticatedUser.class,
                authentication.getPrincipal()
        );
        assertEquals(1L, user.id());
        assertEquals("hong", user.userId());
        assertEquals("홍길동", user.userName());
        assertEquals("ROLE_USER", user.role());
    }
}
