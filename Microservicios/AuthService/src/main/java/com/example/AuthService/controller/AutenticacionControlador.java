package com.example.AuthService.controller;

import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.RegistroResponse;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.LoginResponse;
import com.example.AuthService.service.ServicioAutenticacion;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutenticacionControlador {
    private final ServicioAutenticacion servicioAutenticacion;
    
    @Autowired
    public AutenticacionControlador(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }
    
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = servicioAutenticacion.autenticar(loginRequest);
        return ResponseEntity.ok(loginResponse);
    }
    
    @PostMapping("/registro")
    public ResponseEntity<RegistroResponse> registrar(@Valid @RequestBody RegistroRequest registroRequest) {
        RegistroResponse registroResponse = servicioAutenticacion.registrarUsuario(registroRequest);
        return ResponseEntity.ok(registroResponse);
    }
}