package com.mantap.dashboard.util;

import com.mantap.dashboard.model.entity.UsersEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
@Getter
public class JwtUtil {

    private final SecretKey signingKey;
    private final long expiration;
    private final String issuer;
    private final String audience;

    public JwtUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") long expiration,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.audience}") String audience) {

        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expiration = expiration;
        this.issuer = issuer;
        this.audience = audience;
    }

    public String generateToken(UsersEntity user) {

        Date now = new Date();
        Date expiredAt = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .issuer(issuer)
                .audience()
                .add(audience)
                .and()
                .subject(user.getNip())
                .claim("userId", user.getUserId())
                .issuedAt(now)
                .expiration(expiredAt)
                .signWith(signingKey)
                .compact();
    }

    public Claims parseToken(String token) {

        return Jwts.parser()
                .verifyWith(signingKey)
                .requireIssuer(issuer)
                .requireAudience(audience)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractNip(String token) {
        return parseToken(token).getSubject();
    }
}