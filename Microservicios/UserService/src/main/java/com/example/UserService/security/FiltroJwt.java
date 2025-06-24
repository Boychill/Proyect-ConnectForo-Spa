package com.example.UserService.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class FiltroJwt extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public FiltroJwt(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (header == null || !header.startsWith("Bearer ")) {
            System.out.println("❌ No se encontró token JWT en la cabecera Authorization");
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);
        try {
            if (!jwtUtil.validarToken(token)) {
                System.out.println("❌ Token JWT inválido o expirado");
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido");
                return;
            }

            Claims claims = jwtUtil.obtenerDatos(token);
            Long userId = claims.get("id", Long.class);
            String correo = claims.getSubject();

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    correo, null, null
            );
            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("✅ Token JWT válido. Usuario autenticado: " + correo);

        } catch (Exception e) {
            System.out.println("❌ Excepción al procesar el token JWT: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "Token inválido");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
