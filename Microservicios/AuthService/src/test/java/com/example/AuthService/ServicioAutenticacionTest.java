package com.example.AuthService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.repository.UsuariosRepository;
import com.example.AuthService.security.JwtUtil;
import com.example.AuthService.service.ServicioAutenticacion;

public class ServicioAutenticacionTest {

    @Mock
    private UsuariosRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private ServicioAutenticacion servicioAutenticacion;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void login_deberiaRetornarTokenSiCredencialesSonCorrectas() {
        // Arrange
        String correo = "test@correo.com";
        String clave = "1234";
        LoginRequest request = new LoginRequest();
        request.setCorreo(correo);
        request.setClave(clave);

        Usuarios mockUsuario = new Usuarios();
        mockUsuario.setCorreo(correo);
        mockUsuario.setClave(clave);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(mockUsuario));
        when(jwtUtil.generarToken(mockUsuario)).thenReturn("token-jwt");

        // Act
        String token = servicioAutenticacion.login(request);

        // Assert
        assertNotNull(token);
        assertEquals("token-jwt", token);
    }

    @Test
    void login_deberiaLanzarExcepcionSiCorreoNoExiste() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setCorreo("noexiste@correo.com");
        request.setClave("1234");

        when(usuarioRepository.findByCorreo(request.getCorreo())).thenReturn(Optional.empty());

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            servicioAutenticacion.login(request);
        });

        assertTrue(exception.getMessage().contains("Usuario no encontrado"));
    }

    @Test
    void login_deberiaLanzarExcepcionSiClaveEsIncorrecta() {
        // Arrange
        LoginRequest request = new LoginRequest();
        request.setCorreo("test@correo.com");
        request.setClave("clave-incorrecta");

        Usuarios mockUsuario = new Usuarios();
        mockUsuario.setCorreo("test@correo.com");
        mockUsuario.setClave("clave-correcta");

        when(usuarioRepository.findByCorreo(request.getCorreo())).thenReturn(Optional.of(mockUsuario));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            servicioAutenticacion.login(request);
        });

        assertTrue(exception.getMessage().contains("Contraseña incorrecta"));
    }

    @Test
    void registrar_deberiaGuardarUsuarioSiNoExiste() {
        // Arrange
        RegistroRequest request = new RegistroRequest();
        request.setUsername("nuevoUser");
        request.setCorreo("nuevo@correo.com");
        request.setClave("claveSegura");

        when(usuarioRepository.existsByUsername(request.getUsername())).thenReturn(false);
        when(passwordEncoder.encode(request.getClave())).thenReturn("claveEncriptada");

        // Act
        assertDoesNotThrow(() -> servicioAutenticacion.registrar(request));

        // Assert
        verify(usuarioRepository, times(1)).save(any(Usuarios.class));
    }

    @Test
    void registrar_deberiaLanzarExcepcionSiUsuarioYaExiste() {
        // Arrange
        RegistroRequest request = new RegistroRequest();
        request.setUsername("existente");

        when(usuarioRepository.existsByUsername(request.getUsername())).thenReturn(true);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            servicioAutenticacion.registrar(request);
        });

        assertTrue(exception.getMessage().contains("Usuario ya existe"));
    }
}