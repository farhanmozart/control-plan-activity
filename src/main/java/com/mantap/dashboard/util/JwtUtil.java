package com.mantap.dashboard.util;

import com.mantap.dashboard.model.entity.UsersEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.mantap.dashboard.util.constant.Constant.NIP;
import static com.mantap.dashboard.util.constant.Constant.ROLE_ID;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(UsersEntity user) {

        SecretKey key = Keys.hmacShaKeyFor(
                secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(user.getUserId())
                .claim(NIP, user.getNip())
                .claim(ROLE_ID, user.getRoleId())
                .claim("departmentId", user.getDepartmentId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(key)
                .compact();
    }
}