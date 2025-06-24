package com.example.FilesCommon.files;

import java.security.Key;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {
    private Key secretKey;

    @Value("${jwt.secret}")
    private String secret;

    @PostConstruct
    public void init() {
        // Inicializa la clave secreta para firmar el token
        this.secretKey = Keys.hmacShaKeyFor(Base64.getEncoder().encode(secret.getBytes()));
    }

    // Generar token JWT
    public String generarToken(Long id, String correo, String username, String rol) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", id);
        claims.put("correo", correo);
        claims.put("username", username);
        claims.put("rol", rol);

        return Jwts.builder()
                    .setClaims(claims)
                    .setSubject(String.valueOf(id))
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Expiración de 24 horas
                    .signWith(secretKey, SignatureAlgorithm.HS256)
                    .compact();
    }

    // Validar token JWT
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token); // Intenta parsear el token
            return true;
        } catch (JwtException e) {
            return false;  // Token inválido
        }
    }

    // Obtener los claims del token
    public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
    }
}
