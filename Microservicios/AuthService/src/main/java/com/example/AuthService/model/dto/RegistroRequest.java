package com.example.AuthService.model.dto;

import com.example.AuthService.model.entity.Rol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroRequest {
    private String correo;
    private String clave;
    private String username;
    private Rol rol= Rol.USUARIO;
    private String nombre;
    private String biografia;
    private String fotoPerfil;
}
