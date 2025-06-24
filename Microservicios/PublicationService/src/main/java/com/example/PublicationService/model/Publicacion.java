package com.example.PublicationService.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String contenido;

    private Long forumId;  // Relación con el ID del foro (clave foránea)

    private long mediaId;  // URL del archivo multimedia (imagen o video)

    private Long idUsuario;    // quien creó el foro
    private String username;
}
