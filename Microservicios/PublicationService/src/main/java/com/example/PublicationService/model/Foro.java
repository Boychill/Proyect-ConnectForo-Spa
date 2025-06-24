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

    private Long categoriaId;
}
