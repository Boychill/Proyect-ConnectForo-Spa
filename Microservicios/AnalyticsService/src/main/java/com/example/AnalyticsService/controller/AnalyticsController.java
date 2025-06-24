package com.example.AnalyticsService.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.AnalyticsService.Service.AnalyticsService;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/resumen")
    public Map<String, Integer> obtenerResumen() {
        Map<String, Integer> resumen = new HashMap<>();
        resumen.put("totalTemas", analyticsService.contarTemas());
        resumen.put("totalPublicaciones", analyticsService.contarPublicaciones());
        resumen.put("totalComentarios", analyticsService.contarComentarios());
        return resumen;
    }   
}
