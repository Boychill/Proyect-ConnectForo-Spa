package com.example.AuthService.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistroResponse {
    private String token;
    
    public RegistroResponse(String token) {
        this.token = token;
    }
}