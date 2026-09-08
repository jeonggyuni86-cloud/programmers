package org.example.boardservice.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final String ENV_PREFIX = "JWT_SECRET=";

    private final JwtProperties jwtProperties;

    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        String configuredSecret = jwtProperties.getSecretKey();
        if (configuredSecret == null || configuredSecret.isBlank()) {
            throw new IllegalStateException("jwt.secret-key 설정이 필요합니다.");
        }

        String secret = configuredSecret.startsWith(ENV_PREFIX)
                ? configuredSecret.substring(ENV_PREFIX.length())
                : configuredSecret;

        byte[] keyBytes;
        try {
            keyBytes = Decoders.BASE64.decode(secret);
        } catch (RuntimeException exception) {
            keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        }

        SecretKey secretKey = Keys.hmacShaKeyFor(keyBytes);
        JwtParserBuilder parser = Jwts.parser().verifyWith(secretKey);

        if (jwtProperties.getIssuer() != null && !jwtProperties.getIssuer().isBlank()) {
            parser = parser.requireIssuer(jwtProperties.getIssuer());
        }

        this.jwtParser = parser.build();
    }

    public Claims parse(String token) {
        return jwtParser.parseSignedClaims(token).getPayload();
    }
}
