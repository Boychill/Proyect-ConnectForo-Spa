package com.example.ReportService.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reportes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long idUsuario;         // Usuario que reporta
    private Long idContenido;       // ID del contenido reportado (comentario o publicación)
    private String tipoContenido;   // "comentario", "publicacion"
    private String motivo;          // Texto que explica el motivo
    private LocalDateTime fecha;
}
