package com.example.AuthService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.AuthService.service.*;
import com.example.AuthService.config.ExcepcionesPersonalizadas;
import com.example.AuthService.controller.AutenticacionControlador;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.entity.Rol;
import com.example.AuthService.model.entity.Usuarios;
import com.example.AuthService.repository.UsuariosRepository;
import com.example.AuthService.security.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(MockitoExtension.class)
class ServicioAutenticacionTest {
    
    @Mock
    private UsuariosRepository usuariosRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Spy
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private ServicioAutenticacion servicioAutenticacion;

    @Test
    void loginExitoso() {
        String rawPassword = "1234";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        LoginRequest request = new LoginRequest("correo@ejemplo.com", rawPassword);

        Usuarios usuario = Usuarios.builder()
            .correo("correo@ejemplo.com")
            .clave(encodedPassword)
            .build();

        when(usuariosRepository.findByCorreo("correo@ejemplo.com"))
            .thenReturn(Optional.of(usuario));
        when(jwtUtil.generarToken(any(Usuarios.class)))
            .thenReturn("jwt-token");

        String resultado = servicioAutenticacion.login(request);

        assertEquals("jwt-token", resultado);
    }
    
    @Test
    void loginUsuarioNoExiste() {
        // Preparación
        when(usuariosRepository.findByCorreo(anyString())).thenReturn(Optional.empty());
        
        // Ejecución y Verificación
        assertThrows(ExcepcionesPersonalizadas.class, 
            () -> servicioAutenticacion.login(loginRequest));
        verify(usuariosRepository).findByCorreo(loginRequest.getCorreo());
    }
    
    @Test
    void loginContraseñaIncorrecta() {
        // Preparación
        when(usuariosRepository.findByCorreo(anyString())).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);
        
        // Ejecución y Verificación
        assertThrows(ExcepcionesPersonalizadas.class, 
            () -> servicioAutenticacion.login(loginRequest));
        verify(usuariosRepository).findByCorreo(loginRequest.getCorreo());
        verify(passwordEncoder).matches(loginRequest.getClave(), usuario.getClave());
    }
    
    @Test
    void registroExitoso() {
        // Preparación
        when(usuariosRepository.existsByUsername(anyString())).thenReturn(false);
        when(usuariosRepository.save(any())).thenReturn(usuario);
        
        // Ejecución
        String token = servicioAutenticacion.registrar(registroRequest);
        
        // Verificación
        assertEquals("token-test", token);
        verify(usuariosRepository).existsByUsername(registroRequest.getUsername());
        verify(usuariosRepository).save(any());
        verify(jwtUtil).generarToken(usuario);
    }
    
    @Test
    void registroUsuarioExistente() {
        // Preparación
        when(usuariosRepository.existsByUsername(anyString())).thenReturn(true);
        
        // Ejecución y Verificación
        assertThrows(ExcepcionesPersonalizadas.class, 
            () -> servicioAutenticacion.registrar(registroRequest));
        verify(usuariosRepository).existsByUsername(registroRequest.getUsername());
    }
}