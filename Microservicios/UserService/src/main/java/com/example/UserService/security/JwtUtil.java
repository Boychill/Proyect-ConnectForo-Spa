package com.example.UserService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key secretKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        byte[] keyBytes = Base64.getEncoder().encode(secret.getBytes());
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    public Claims obtenerDatos(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .setAllowedClockSkewSeconds(300) // 5 minutos de tolerancia
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean validarToken(String token) {
        try {
            Claims claims = obtenerDatos(token);
            Date expiracion = claims.getExpiration();
            Date ahora = new Date();
            System.out.println("✅ Token válido. Expira en: " + expiracion + " | Hora actual: " + ahora);
            return expiracion.after(ahora);
        } catch (Exception e) {
            System.out.println("❌ Error al validar el token JWT: " + e.getMessage());
            return false;
        }
    }
}

