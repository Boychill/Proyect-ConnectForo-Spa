package com.example.AuthService.config;

import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.repository.UsuariosRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DatosPrecargados {

    private final UsuariosRepository usuariosRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        if (!usuariosRepository.existsByUsername("admin")) {
            String encodedPassword = passwordEncoder.encode("admin123");

            Usuarios admin = Usuarios.builder()
                    .username("admin")
                    .correo("admin@admin.com")
                    .clave(encodedPassword)
                    .rol(Rol.ADMIN)
                    .nombre("Administrador")
                    .biografia("Administrador del sistema")
                    .fotoPerfil("default.jpg")
                    .activo(true)
                    .build();

            usuariosRepository.save(admin);
            System.out.println("✔️ Administrador pre-cargado creado con éxito.");
        }
    }
}
