package com.example.AnalyticsService.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AnalyticsService {

    @Autowired
    private RestTemplate restTemplate;

    public int contarTemas() {
        String url = "http://localhost:8083/api/temas";
        Object[] temas = restTemplate.getForObject(url, Object[].class);
        return temas != null ? temas.length : 0;
    }

    public int contarPublicaciones() {
        String url = "http://localhost:8083/api/publicaciones";
        Object[] publicaciones = restTemplate.getForObject(url, Object[].class);
        return publicaciones != null ? publicaciones.length : 0;
    }

    public int contarComentarios() {
        String url = "http://localhost:8084/api/comentarios";
        Object[] comentarios = restTemplate.getForObject(url, Object[].class);
        return comentarios != null ? comentarios.length : 0;
    }
    
}
