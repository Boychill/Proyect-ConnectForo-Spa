package com.example.CategoryService.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // Configuración de seguridad HTTP
        http.csrf().disable()  // Deshabilita CSRF para APIs REST
            .authorizeRequests()
                // Protege el endpoint de categorías solo para ADMIN
                .requestMatchers("/api/categorias").hasAuthority("ROLE_USER")  // Solo rol USER puede acceder a /api/categorias
                // Protege los endpoints POST, PUT y DELETE solo para ADMIN
                .requestMatchers("/api/categorias/**").hasAuthority("ROLE_ADMIN")  // Solo ADMIN puede acceder a esos
                // Permite acceso a otros endpoints
                .anyRequest().permitAll()
            .and()
            .addFilterBefore(new FiltroJwt(jwtUtil), UsernamePasswordAuthenticationFilter.class); // Usar el filtro JWT

        return http.build();  // Devuelve la configuración
    }
}