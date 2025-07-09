package com.example.ForumService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ForumService.model.Foro;
import com.example.ForumService.repository.ForoRepository;

@Service
public class ForoService {

    @Autowired
    private ForoRepository foroRepository;

    public Foro guardar(Foro foro) {
        return foroRepository.save(foro);
    }

    public Optional<Foro> buscarPorId(Long id) {
        return foroRepository.findById(id);
    }

    public List<Foro> listarTodos() {
        return foroRepository.findAll();
    }

    public List<Foro> listarPorCategoria(Long categoriaId) {
        return foroRepository.findByCategoriaId(categoriaId);
    }

    public void eliminar(Long id) {
        foroRepository.deleteById(id);
    }
}
