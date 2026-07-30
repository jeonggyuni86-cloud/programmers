package com.example.tokenproject.config.jwt;

import com.example.tokenproject.domain.entity.Role;
import com.example.tokenproject.domain.entity.User;
import com.example.tokenproject.security.CustomUserDetails;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {
    private static final String CLAIM_ID = "id";
    private static final String CLAIM_ROLE = "role";
    private static final String CLAIM_NAME = "name";

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(
            User user,
            Duration expiration
    ) {
        Date now = new Date();
        return makeToken(user, new Date(now.getTime() + expiration.toMillis()));
    }

    private String makeToken(
            User user,
            Date expiration
    ) {
        Date now = new Date();
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(now)
                .expiration(expiration)
                .subject(user.getUserId())
                .claim(CLAIM_ID, user.getId())
                .claim(CLAIM_ROLE, user.getRole().name())
                .claim(CLAIM_NAME, user.getName())
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public TokenStatus validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            return TokenStatus.VALID;
        } catch(ExpiredJwtException e) {
            return TokenStatus.EXPIRED;
        } catch(Exception e) {
            return TokenStatus.INVALID;
        }
    }

    public User getTokenDetails(String token) {
        Claims claims = getClaims(token);
        return User.builder()
                .id(claims.get(CLAIM_ID, Long.class))
                .userId(claims.getSubject())
                .name(claims.get(CLAIM_NAME, String.class))
                .role(Role.valueOf(claims.get(CLAIM_ROLE, String.class)))
                .build();
    }

    public Authentication getAuthentication(
            User user,
            String token
    ) {
        var principal = CustomUserDetails.builder()
                .user(user)
                .build();
        return new UsernamePasswordAuthenticationToken(principal, token);
    }


    private Claims getClaims(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }
}
