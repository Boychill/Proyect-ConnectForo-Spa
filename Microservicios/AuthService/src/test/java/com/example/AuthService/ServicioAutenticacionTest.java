package com.example.AuthService;


import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.repository.UsuariosRepository;
import com.example.AuthService.security.JwtUtil;
import com.example.AuthService.service.ServicioAutenticacion;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioAutenticacionTest {

    @Mock private UsuariosRepository usuarioRepository;
    @Mock private JwtUtil jwtUtil;
    @Mock private PasswordEncoder passwordEncoder;

    @InjectMocks private ServicioAutenticacion servicioAutenticacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginExitoso() {
        LoginRequest request = new LoginRequest("correo@ejemplo.com", "clave123");
        Usuarios usuario = Usuarios.builder()
                .id(1L)
                .correo("correo@ejemplo.com")
                .clave("encriptado")
                .rol(Rol.USUARIO)
                .build();

        when(usuarioRepository.findByCorreo("correo@ejemplo.com")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("clave123", "encriptado")).thenReturn(true);
        when(jwtUtil.generarToken(usuario)).thenReturn("token");

        assertEquals("token", servicioAutenticacion.autenticar(request).getToken());
    }

   @Test
void registroExitoso() {
    RegistroRequest request = new RegistroRequest();
    request.setUsername("usuario");
    request.setCorreo("nuevo@correo.com");
    request.setClave("clave123");

    when(usuarioRepository.existsByUsername("usuario")).thenReturn(false);
    when(passwordEncoder.encode("clave123")).thenReturn("claveEncriptada");

    Usuarios usuarioGuardado = Usuarios.builder()
            .id(1L)
            .username("usuario")
            .correo("nuevo@correo.com")
            .clave("claveEncriptada")
            .rol(Rol.USUARIO)
            .build();

    when(usuarioRepository.save(any(Usuarios.class))).thenReturn(usuarioGuardado);
    when(jwtUtil.generarToken(any())).thenReturn("nuevoToken");

    assertEquals("nuevoToken", servicioAutenticacion.registrarUsuario(request).getToken());
}

}
