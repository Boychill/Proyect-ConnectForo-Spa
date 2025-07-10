package com.example.CommentService.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.CommentService.model.Comentario;
import com.example.CommentService.repository.ComentarioRepository;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioRepository repository;

    public Comentario save(Comentario comentario) {
        return repository.save(comentario);
    }

    public Optional<Comentario> findById(Long id) {
        return repository.findById(id);
    }

    public List<Comentario> findByPublication(Long publicationId) {
        return repository.findByPublicationId(publicationId);
    }

    public List<Comentario> findAll() {
        return repository.findAll();
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}

