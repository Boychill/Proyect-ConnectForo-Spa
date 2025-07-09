package com.example.AuthService;

import com.example.AuthService.controller.AutenticacionControlador;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.LoginResponse;
import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.RegistroResponse;
import com.example.AuthService.security.FiltroJwt;
import com.example.AuthService.service.ServicioAutenticacion;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = AutenticacionControlador.class,
    excludeFilters = @ComponentScan.Filter(
        type = FilterType.ASSIGNABLE_TYPE,
        classes = FiltroJwt.class
    )
)
@AutoConfigureMockMvc(addFilters = false)
class AutenticacionControladorTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioAutenticacion servicioAutenticacion;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void loginExitoso() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setCorreo("correo@ejemplo.com");
        loginRequest.setClave("clave123");

        LoginResponse loginResponse = new LoginResponse("token-valido");

        when(servicioAutenticacion.autenticar(any(LoginRequest.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-valido"));
    }

    @Test
    void registroExitoso() throws Exception {
        RegistroRequest registroRequest = new RegistroRequest();
        registroRequest.setUsername("usuarioNuevo");
        registroRequest.setCorreo("nuevo@correo.com");
        registroRequest.setClave("clave123");

        RegistroResponse registroResponse = new RegistroResponse("token-registro");

        when(servicioAutenticacion.registrarUsuario(any(RegistroRequest.class))).thenReturn(registroResponse);

        mockMvc.perform(post("/api/auth/registro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registroRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-registro"));
    }
}
