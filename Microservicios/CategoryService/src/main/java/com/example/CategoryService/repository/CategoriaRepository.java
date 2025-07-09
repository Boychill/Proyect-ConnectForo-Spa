package com.example.CategoryService.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.CategoryService.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> findByNombre(String nombre);
}