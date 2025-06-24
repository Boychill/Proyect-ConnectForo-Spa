package com.example.NotificationService.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Notificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tipo; // "Publicacion", "Comentario", etc.

    private String mensaje; 

    private String destinatario; // email o nombre del usuario

    private LocalDateTime fechaCreacion; 

    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
    
    }
}
