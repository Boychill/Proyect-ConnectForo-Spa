package com.example.AuthService.config;

import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.repository.UsuariosRepository;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class DatosPrecargados {
    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        try {
            // Crear usuario administrador
            if (usuariosRepository.findByUsername("admin") == null && 
                usuariosRepository.findByCorreo("admin@admin.com") == null) {
                Usuarios admin = Usuarios.builder()
                    .username("admin")
                    .correo("admin@admin.com")
                    .clave(passwordEncoder.encode("admin123"))
                    .rol(Rol.ADMIN)
                    .nombre("Administrador del Sistema")
                    .biografia("Usuario administrador del sistema")
                    .fotoPerfil("default.jpg")
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();
                
                usuariosRepository.save(admin);
                log.info("Usuario administrador creado con éxito");
            }

            // Crear usuario normal
            if (usuariosRepository.findByUsername("sopaipaconkapo") == null && 
                usuariosRepository.findByCorreo("sopaipa@admin.com") == null) {
                Usuarios normal = Usuarios.builder()
                    .username("sopaipaconkapo")
                    .correo("sopaipa@admin.com")
                    .clave(passwordEncoder.encode("123"))
                    .rol(Rol.USUARIO)
                    .nombre("Usuario Normal")
                    .biografia("Usuario regular del sistema")
                    .fotoPerfil("default.jpg")
                    .activo(true)
                    .fechaRegistro(LocalDateTime.now())
                    .build();
                
                usuariosRepository.save(normal);
                log.info("Usuario normal creado con éxito");
            }
        } catch (Exception e) {
            log.error("Error al precargar datos: {}", e.getMessage());
        }
    }
}