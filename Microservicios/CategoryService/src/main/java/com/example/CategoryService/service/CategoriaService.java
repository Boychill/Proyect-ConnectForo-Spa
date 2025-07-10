package com.example.CategoryService.service;

import com.example.CategoryService.model.Categoria;
import com.example.CategoryService.repository.CategoriaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    public Categoria crearCategoria(Categoria categoria) {
        if (repository.findByNombre(categoria.getNombre()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoría con ese nombre");
        }
        return repository.save(categoria);
    }

    public List<Categoria> obtenerTodas() {
        return repository.findAll();
    }

    public Optional<Categoria> obtenerPorId(Long id) {
        return repository.findById(id);
    }

    public Categoria actualizarCategoria(Long id, Categoria nuevaCategoria) {
        return repository.findById(id).map(cat -> {
            cat.setNombre(nuevaCategoria.getNombre());
            return repository.save(cat);
        }).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
    }

    public void eliminarCategoria(Long id) {
        Categoria categoria = repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada"));
        repository.delete(categoria);
    }

    public Optional<Categoria> obtenerPorNombre(String nombre) {
        return repository.findByNombre(nombre);
    }
}
