package com.example.ForumService.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ForumService.controller.ForoController.CategoriaClient;
import com.example.ForumService.model.Categoria;
import com.example.ForumService.model.Foro;
import com.example.ForumService.model.ForoDTO;
import com.example.ForumService.repository.ForoRepository;

@Service
public class ForoService {
    private final ForoRepository foroRepository;
    private final CategoriaClient categoriaClient;


    @Autowired

    public ForoService(ForoRepository foroRepository, CategoriaClient categoriaClient) {
        this.foroRepository = foroRepository;
        this.categoriaClient = categoriaClient;
    }
     public List<ForoDTO> obtenerForosConCategorias() {
        List<Foro> foros = foroRepository.findAll();
        List<ForoDTO> foroDTOs = new ArrayList<>();

        // Obtener todas las categorías desde CategoryService
        List<Categoria> categorias = categoriaClient.obtenerTodasLasCategorias();

        // Mapear los foros con las categorías
        for (Foro foro : foros) {
            Categoria categoria = categorias.stream()
                    .filter(c -> c.getId().equals(foro.getCategoriaId()))
                    .findFirst()
                    .orElse(null);

            if (categoria != null) {
                ForoDTO foroDTO = new ForoDTO();
                foroDTO.setId(foro.getId());
                foroDTO.setTitulo(foro.getTitulo());
                foroDTO.setDescripcion(foro.getDescripcion());
                foroDTO.setCategoriaId(categoria.getId());
                foroDTO.setCategoriaNombre(categoria.getNombre());

                foroDTOs.add(foroDTO);
            }
        }

        return foroDTOs;
    }
    public List<ForoDTO> obtenerForosPorCategoria(Long categoriaId) {
        // Recuperamos todos los foros con el ID de categoría proporcionado
        List<Foro> foros = foroRepository.findByCategoriaId(categoriaId);
        List<ForoDTO> foroDTOs = new ArrayList<>();

        if (foros.isEmpty()) {
            throw new RuntimeException("No se encontraron foros para la categoría con ID: " + categoriaId);
        }

        // Obtener las categorías solo una vez para evitar hacer múltiples solicitudes
        Categoria categoria = categoriaClient.obtenerCategoria(categoriaId);

        // Validar que la categoría existe
        if (categoria == null) {
            throw new RuntimeException("Categoría no encontrada para el ID: " + categoriaId);
        }

        // Mapear los foros a ForoDTO
        for (Foro foro : foros) {
            ForoDTO foroDTO = new ForoDTO();
            foroDTO.setId(foro.getId());
            foroDTO.setTitulo(foro.getTitulo());
            foroDTO.setDescripcion(foro.getDescripcion());
            foroDTO.setCategoriaId(categoria.getId());
            foroDTO.setCategoriaNombre(categoria.getNombre());

            foroDTOs.add(foroDTO);
        }

        return foroDTOs;
    }
    public Optional<Foro> obtenerForosPorId(Long id){
        return foroRepository.findById(id);
    }
}