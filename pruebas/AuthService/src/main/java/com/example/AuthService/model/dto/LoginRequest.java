package com.example.AuthService.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "El correo es obligatorio")
    private String correo;
    
    @NotBlank(message = "La contraseña es obligatoria")
    private String clave;
}