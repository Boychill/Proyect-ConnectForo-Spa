package com.example.ForumService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ForumService.model.Categoria;
import com.example.ForumService.model.Foro;
import com.example.ForumService.model.ForoDTO;
import com.example.ForumService.service.ForoService;


@RestController
@RequestMapping("/api/foros")
public class ForoController {
    
    private final ForoService foroService;

    @Autowired
    public ForoController(ForoService foroService) {
        this.foroService = foroService;
    }

    @GetMapping("/")
    public List<ForoDTO> obtenerForos() {
        return foroService.obtenerForosConCategorias();
    }
    @GetMapping("/{id}")
    public Foro obtenerPorId(@PathVariable Long id) {
        return foroService.obtenerForosPorId(id).orElse(null);
    }

    @FeignClient(name = "category-service", url = "http://localhost:8081")
    public interface CategoriaClient {

        @GetMapping("/categorias")
        List<Categoria> obtenerTodasLasCategorias(); // Devuelve todas las categorías

        @GetMapping("/categorias/{id}")
        Categoria obtenerCategoria(@PathVariable("id") Long id);
        
    }

}
