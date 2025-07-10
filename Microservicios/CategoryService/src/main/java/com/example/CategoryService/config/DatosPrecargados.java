package com.example.CategoryService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.CategoryService.model.Categoria;
import com.example.CategoryService.service.CategoriaService;

import jakarta.annotation.PostConstruct;

@Component
public class DatosPrecargados {


    @Autowired
    private CategoriaService categoriaService;

    @PostConstruct
    public void cargarDatosIniciales() {
        if (categoriaService.obtenerTodas().isEmpty()) {
            categoriaService.crearCategoria(Categoria.builder()
                    .nombre("Tecnología")
                    .build());

            categoriaService.crearCategoria(Categoria.builder()
                    .nombre("Educación")
                    .build());

            categoriaService.crearCategoria(Categoria.builder()
                    .nombre("Ciberseguridad")
                    .build());
        }
    }
}