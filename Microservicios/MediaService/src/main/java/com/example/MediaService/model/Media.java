package com.example.MediaService.model;

import java.util.Date;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Data
@Table(name = "media")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String url;
    private String tipo; // MIME: image/png, video/mp4
    private Long tamaño;

    // Asociación polimórfica
    private Long referenciaId; // puede ser el ID del usuario o publicación
    private String referenciaTipo; // "usuario" o "foro"

    private Date fechaCreacion;
}

