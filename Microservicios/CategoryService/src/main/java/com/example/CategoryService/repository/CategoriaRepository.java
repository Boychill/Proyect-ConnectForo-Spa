package com.example.CategoryService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CategoryService.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    List<Categoria> findByIdCategoria(Long idCategoria);
    Optional<Categoria> findByNombre(String nombre);
}
