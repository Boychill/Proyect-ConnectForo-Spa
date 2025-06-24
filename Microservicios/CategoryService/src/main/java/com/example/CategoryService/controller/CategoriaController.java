package com.example.CategoryService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.CategoryService.model.Categoria;
import com.example.CategoryService.service.CategoriaService;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService service;

     //Anotacion para que conlleve la seguridad de los endpoints segunlos roles
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public Categoria crear(@RequestBody Categoria categoria) {
        return service.crearCategoria(categoria);
    }
    
    @GetMapping
    public List<Categoria> listar() {
        return service.obtenerTodas();
    }
    
    @GetMapping("/{id}")
    public Categoria obtenerPorId(@PathVariable Long id) {
        return service.obtenerPorId(id).orElse(null);
    }
    @PutMapping("/{id}")
    public Categoria actualizar(@PathVariable Long id, @RequestBody Categoria nuevaCategoria) {
        return service.actualizarCategoria(id, nuevaCategoria);
    }
    
    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminarCategoria(id);
    }
    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<Categoria> obtenerPorNombre(@PathVariable String nombre) {
        return service.obtenerPorNombre(nombre)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
}