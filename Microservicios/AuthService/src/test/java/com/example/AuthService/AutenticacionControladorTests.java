package com.example.AuthService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.example.AuthService.service.*;
import com.example.AuthService.controller.AutenticacionControlador;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.RegistroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;


@WebMvcTest(AutenticacionControlador.class)
@Import(TestSecurityConfig.class)
@ContextConfiguration(classes = com.example.AuthService.AuthServiceApplication.class)
public class AutenticacionControladorTests {

    @Autowired  
    private MockMvc mockMvc;

    @MockBean
    private ServicioAutenticacion servicioAutenticacion;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void login_deberiaRetornarTokenSiCredencialesSonValidas() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setCorreo("test@correo.com");
        request.setClave("1234");

        when(servicioAutenticacion.login(any(LoginRequest.class))).thenReturn("jwt-token-falso");

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.token").value("jwt-token-falso"));
    }

    @Test
    void login_deberiaRetornar401SiCredencialesSonInvalidas() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setCorreo("mal@correo.com");
        request.setClave("incorrecta");

        when(servicioAutenticacion.login(any(LoginRequest.class)))
            .thenThrow(new RuntimeException("Credenciales inválidas"));

        mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isUnauthorized())
            .andExpect(content().string("Credenciales inválidas"));
    }

    @Test
    void registrar_deberiaRetornar201SiUsuarioEsRegistrado() throws Exception {
        RegistroRequest request = new RegistroRequest();
        request.setUsername("nuevoUsuario");
        request.setCorreo("nuevo@correo.com");
        request.setClave("123456");

        doNothing().when(servicioAutenticacion).registrar(any(RegistroRequest.class));

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isCreated())
            .andExpect(content().string("Usuario registrado con éxito"));
    }

    @Test
    void registrar_deberiaRetornar400SiRegistroFalla() throws Exception {
        RegistroRequest request = new RegistroRequest();
        request.setUsername("existente");
        request.setCorreo("existente@correo.com");
        request.setClave("123456");

        doThrow(new RuntimeException("Usuario ya existe"))
        .when(servicioAutenticacion)
        .registrar(any(RegistroRequest.class));

        mockMvc.perform(post("/api/auth/registro")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isBadRequest())
            .andExpect(content().string("Error al registrar: Usuario ya existe"));
    }
}
