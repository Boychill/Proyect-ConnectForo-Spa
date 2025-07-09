package com.example.AuthService.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExcepcionesPersonalizadas extends RuntimeException {
    public ExcepcionesPersonalizadas(String mensaje) {
        super(mensaje);
    }
}
