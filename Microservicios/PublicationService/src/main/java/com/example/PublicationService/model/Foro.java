package com.example.PublicationService.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foro {
    private Long id;

    private String titulo;
    private String descripcion;

    private Long idCategoria;  // id de la categoría

    private Long idUsuario;    // quien creó el foro
    private String username;
}
