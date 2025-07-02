package com.example.AuthService.security;

import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import com.example.AuthService.model.entity.Usuarios;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mi-clave-secreta";

    public String generarToken(Usuarios usuario) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", usuario.getUsername());
        claims.put("correo", usuario.getCorreo());
        claims.put("rol", usuario.getRol().name());

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(usuario.getId().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10h
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token) {
        try {
            extractAllClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserId(String token) {
        return extractAllClaims(token).getSubject();
    }

    public String getUsername(String token) {
        return extractAllClaims(token).get("username", String.class);
    }

    public String getCorreo(String token) {
        return extractAllClaims(token).get("correo", String.class);
    }

    public String getRol(String token) {
        return extractAllClaims(token).get("role", String.class);
    }
}