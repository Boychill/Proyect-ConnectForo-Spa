package com.example.AuthService.config;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class ExcepcionesPersonalizadas extends RuntimeException {
    public ExcepcionesPersonalizadas(String message) {
        super(message);
   }
}