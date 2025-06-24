package com.example.PublicationService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.PublicationService.model.Publicacion;
import com.example.PublicationService.repository.PublicacionRepository;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository repository;

    public Publicacion guardar(Publicacion p) {
        return repository.save(p);
    }

    public Optional<Publicacion> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public List<Publicacion> listarPorForo(Long idForo) {
        return repository.findByForumId(idForo);
    }

    public List<Publicacion> listarTodas() {
        return repository.findAll();
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}