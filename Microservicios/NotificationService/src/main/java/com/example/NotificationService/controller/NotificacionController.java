package com.example.NotificationService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.NotificationService.model.Notificacion;
import com.example.NotificationService.repository.NotificacionRepository;

@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionRepository notificacionRepository;

    // Obtener todas las notificaciones
    @GetMapping
    public List<Notificacion> getAll() {
        return notificacionRepository.findAll();
    }

    // Obtener notificaciones por destinatario
    @GetMapping("/destinatario/{destinatario}")
    public List<Notificacion> getByDestinatario(@PathVariable String destinatario) {
        return notificacionRepository.findByDestinatario(destinatario);
    }

    // Crear una nueva notificación
    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion) {
        Notificacion guardada = notificacionRepository.save(notificacion);
        return ResponseEntity.ok(guardada);
    }
}