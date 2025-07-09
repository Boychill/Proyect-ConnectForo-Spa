package com.example.AuthService.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.example.AuthService.model.entity.Usuarios;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j

public class JwtUtil {
    private final String secretKey;
    private final Duration expiration;

    public String generarToken(Usuarios usuario) {
        return Jwts.builder()
            .setSubject(usuario.getId().toString())
            .setIssuedAt(new Date())
            .setExpiration(Date.from(Instant.now().plus(expiration)))
            .claim("username", usuario.getUsername())
            .claim("email", usuario.getCorreo())
            .claim("role", usuario.getRol().name())
            .signWith(SignatureAlgorithm.HS256, secretKey)
            .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean isValid(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            log.error("Token JWT inválido: {}", e.getMessage());
            return false;
        }
    }
}