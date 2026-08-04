package com.example.oauth2_basic_board.config.jwt;

import com.example.oauth2_basic_board.config.security.CustomUserDetails;
import com.example.oauth2_basic_board.domain.entity.Member;
import com.example.oauth2_basic_board.domain.entity.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final String CLAIM_ID = "id";
    private static final String CLAIM_NAME = "name";
    private static final String CLAIM_ROLE = "role";

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(Member user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(
                user,
                new Date(now.getTime() + expiredAt.toMillis())
        );
    }

    private String makeToken(Member user, Date expire) {
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expire)
                .subject(user.getUserId())
                .claim(CLAIM_ID, user.getId())
                .claim(CLAIM_NAME, user.getUserName())
                .claim(CLAIM_ROLE, user.getRole().name())
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public TokenStatus validateToken(String token) {
        try {
            jwtParser.parseSignedClaims(token);
            log.debug("Token is Valid");
            return TokenStatus.VALID;
        } catch(ExpiredJwtException e) {
            log.warn("Token is Expired");
            return TokenStatus.EXPIRED;
        } catch(Exception e) {
            log.warn("Token is Invalid");
            return TokenStatus.INVALID;
        }
    }

    public Member getTokenDetails(String token) {
        var claims = getClaims(token);
        return Member.createUser(
                claims.get(CLAIM_ID, Long.class),
                claims.getSubject(),
                claims.get(CLAIM_NAME, String.class),
                Role.valueOf(claims.get(CLAIM_ROLE, String.class))
        );
    }
    private Claims getClaims(String token) {
        return jwtParser
                .parseSignedClaims(token)
                .getPayload();
    }

    public Authentication getAuthentication(Member user, String token) {
        var principal = CustomUserDetails.builder()
                .user(user)
                .build();

        return new UsernamePasswordAuthenticationToken(principal, token, principal.getAuthorities());
    }

}
