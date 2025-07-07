package com.example.AuthService.controller;

import com.example.AuthService.model.dto.RegistroRequest;
import com.example.AuthService.model.dto.LoginRequest;
import com.example.AuthService.service.ServicioAutenticacion;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AutenticacionControlador {

    @Autowired
    private ServicioAutenticacion servicioAutenticacion;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            String token = servicioAutenticacion.login(request);
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody RegistroRequest request) {
        try {
            servicioAutenticacion.registrar(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario registrado con éxito");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al registrar: " + e.getMessage());
        }
    }
}