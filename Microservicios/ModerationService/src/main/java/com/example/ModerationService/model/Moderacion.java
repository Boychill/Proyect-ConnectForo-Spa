package com.example.ModerationService.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "moderaciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Moderacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // relacion con el reporte
    private Long idReporte;

    // Detalles que simulan el "ticket"
    @Column(length = 500)
    private String motivoReporte;  // Lo trae el microservicio ReportService

    @Column(length = 500)
    private String comentarioModerador;

    // Estado de la moderación
    private String estado; // pendiente, aceptado, rechazado, bloqueado

    // Moderador asignado (puede ser el username o ID)
    private String moderador;

    @Column(name = "fecha_revision")
    private String fechaRevision;
}
