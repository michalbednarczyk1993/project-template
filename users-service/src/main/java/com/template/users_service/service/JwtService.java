package com.template.users_service.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
    @Value("${jwt.secret:dev-jwt-secret}")
    private String jwtSecret;

    @Value("${jwt.expiration.ms:86400000}") // 1 dzień domyślnie
    private long jwtExpirationMs;

    public String generateToken(String email, boolean isAdmin) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("isAdmin", isAdmin);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    public boolean extractIsAdmin(String token) {
        Object isAdmin = extractAllClaims(token).get("isAdmin");
        return isAdmin != null && Boolean.parseBoolean(isAdmin.toString());
    }

    public boolean isTokenValid(String token) {
        try {
            Claims claims = extractAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}