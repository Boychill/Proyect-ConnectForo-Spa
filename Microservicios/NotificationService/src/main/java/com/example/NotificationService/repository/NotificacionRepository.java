package com.example.NotificationService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.NotificationService.model.Notificacion;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByDestinatario(String destinatario);
}
