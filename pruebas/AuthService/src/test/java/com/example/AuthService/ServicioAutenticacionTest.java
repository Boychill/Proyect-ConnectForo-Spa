package com.example.AuthService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.AuthService.config.ExcepcionesPersonalizadas;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.LoginResponse;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.RegistroResponse;
import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.repository.UsuariosRepository;
import com.example.AuthService.security.JwtUtil;
import com.example.AuthService.service.ServicioAutenticacion;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ServicioAutenticacionTest {
    
    @Mock
    private UsuariosRepository usuariosRepository;
    
    @Mock
    private JwtUtil jwtUtil;
    
    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @InjectMocks
    private ServicioAutenticacion servicioAutenticacion;
    
    @BeforeEach
    void setUp() {
        when(jwtUtil.generarToken(any())).thenReturn("token-prueba");
    }
    
    @Test
    void loginExitoso() {
        // Dado
        String correo = "usuario@test.com";
        String clave = "123456";
        
        LoginRequest loginRequest = new LoginRequest(correo, clave);
        String claveEncriptada = passwordEncoder.encode(clave);
        
        Usuarios usuarioEsperado = new Usuarios();
        usuarioEsperado.setCorreo(correo);
        usuarioEsperado.setClave(claveEncriptada);
        
        when(usuariosRepository.findByCorreo(correo))
            .thenReturn(Optional.of(usuarioEsperado));
            
        // Cuando
        LoginResponse resultado = servicioAutenticacion.login(loginRequest);
        
        // Entonces
        assertEquals("token-prueba", resultado.getToken());
        verify(usuariosRepository).findByCorreo(correo);
        verify(jwtUtil).generarToken(usuarioEsperado);
    }
    
    @Test
    void loginUsuarioNoExiste() {
        // Dado
        String correo = "noexistente@test.com";
        LoginRequest loginRequest = new LoginRequest(correo, "clave");
        
        when(usuariosRepository.findByCorreo(correo))
            .thenReturn(Optional.empty());
            
        // Cuando y Entonces
        ExcepcionesPersonalizadas exception = assertThrows(
            ExcepcionesPersonalizadas.class,
            () -> servicioAutenticacion.login(loginRequest)
        );
        assertTrue(exception.getMessage().contains("Usuario no encontrado"));
    }
    
    @Test
    void loginContraseñaIncorrecta() {
        // Dado
        String correo = "usuario@test.com";
        String claveIncorrecta = "contrasenaMal";
        String claveCorrecta = passwordEncoder.encode("contrasenaBien");
        
        LoginRequest loginRequest = new LoginRequest(correo, claveIncorrecta);
        Usuarios usuario = new Usuarios();
        usuario.setCorreo(correo);
        usuario.setClave(claveCorrecta);
        
        when(usuariosRepository.findByCorreo(correo))
            .thenReturn(Optional.of(usuario));
        
        // Cuando y Entonces
        ExcepcionesPersonalizadas exception = assertThrows(
            ExcepcionesPersonalizadas.class,
            () -> servicioAutenticacion.login(loginRequest)
        );
        assertTrue(exception.getMessage().contains("Contraseña incorrecta"));
    }
    
    @Test
    void registroExitoso() {
        // Dado
        RegistroRequest registroRequest = new RegistroRequest(
            "usuarioNuevo",
            "nuevo@correo.com",
            "clave12345",
            "Nombre Usuario",
            "Biografía del usuario",
            "foto.jpg",
            Rol.USUARIO
        );
        
        Usuarios usuarioEsperado = new Usuarios();
        usuarioEsperado.setUsername(registroRequest.getUsername());
        usuarioEsperado.setCorreo(registroRequest.getCorreo());
        usuarioEsperado.setRol(Rol.USUARIO);
        usuarioEsperado.setActivo(true);
        
        when(usuariosRepository.existsByUsername(registroRequest.getUsername()))
            .thenReturn(false);
        when(usuariosRepository.save(any()))
            .thenAnswer(invocation -> {
                Usuarios usuarioGuardado = invocation.getArgument(0);
                usuarioGuardado.setClave(passwordEncoder.encode(registroRequest.getClave()));
                return usuarioGuardado;
            });
            
        // Cuando
        RegistroResponse resultado = servicioAutenticacion.registrar(registroRequest);
        
        // Entonces
        assertEquals("token-prueba", resultado.getToken());
        verify(usuariosRepository).existsByUsername(registroRequest.getUsername());
        verify(usuariosRepository).save(any());
        verify(jwtUtil).generarToken(any());
    }
}