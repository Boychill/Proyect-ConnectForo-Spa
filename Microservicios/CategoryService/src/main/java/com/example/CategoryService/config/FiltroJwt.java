package com.example.CategoryService.config;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FiltroJwt extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public FiltroJwt(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @PostConstruct
    public void init() {
        System.out.println("FiltroJwt ACTIVADO");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {

        System.out.println("PATH: " + request.getRequestURI());

        String path = request.getRequestURI();
        // Excluir las rutas de autenticación del filtro (por ejemplo, login)
        if (path.startsWith("/api/auth")) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // Si no hay token o está mal formado
            return;
        }

        String token = header.substring(7);  // Extraer el token de la cabecera
        if (!jwtUtil.validarToken(token)) {  // Validar el token JWT
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // Si el token es inválido
            return;
        }

        // Si el token es válido, obtener los claims
        Claims claims = jwtUtil.getClaims(token);
        String correo = claims.get("correo", String.class);
        String username = claims.get("username", String.class);
        String rol = claims.get("rol", String.class); // roles como: "USER", "MOD", "ADMIN"

        // Crear una autenticación basada en los roles obtenidos del token
        var auth = new UsernamePasswordAuthenticationToken(
            correo,  // El correo es utilizado como username
            username,  // No necesitamos la contraseña
            List.of(new SimpleGrantedAuthority("ROLE_" + rol))  // Asignamos el rol a la autoridad
        );

        // Establecer la autenticación en el contexto de seguridad
        SecurityContextHolder.getContext().setAuthentication(auth);
        chain.doFilter(request, response);  // Continuar con la cadena de filtros
    }
}

