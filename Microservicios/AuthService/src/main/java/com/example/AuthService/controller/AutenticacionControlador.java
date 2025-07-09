package com.example.AuthService.controller;

import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.RegistroResponse;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.model.dto.LoginResponse;
import com.example.AuthService.service.ServicioAutenticacion;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth") // ← ahora sigue la convención REST
@CrossOrigin(origins = "*", maxAge = 3600)
public class AutenticacionControlador {

    private final ServicioAutenticacion servicioAutenticacion;

    public AutenticacionControlador(ServicioAutenticacion servicioAutenticacion) {
        this.servicioAutenticacion = servicioAutenticacion;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = servicioAutenticacion.autenticar(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/registro")
    public ResponseEntity<RegistroResponse> registrar(@Valid @RequestBody RegistroRequest request) {
        RegistroResponse response = servicioAutenticacion.registrarUsuario(request);
        return ResponseEntity.ok(response);
    }
}
