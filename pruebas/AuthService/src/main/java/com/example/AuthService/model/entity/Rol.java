package com.example.AuthService.model.entity;

import jakarta.persistence.Table;

@Table(name ="Rol")
public enum Rol {
    INVITADO,
    USUARIO,
    MODERADOR,
    ADMIN,
    SUPERADMIN
}
