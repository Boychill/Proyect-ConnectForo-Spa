package com.example.CommentService.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.CommentService.model.Comentario;
import com.example.CommentService.repository.ComentarioRepository;

@Component
public class DatosPrecargados implements CommandLineRunner {

    @Autowired
    private ComentarioRepository repository;
    @Autowired
    private Comentario comentario;

    @Override
    public void run(String... args) {
        List<Comentario> comentarios = List.of(
            comentario.builder().publicationId(1L).userId(1L).username("usuario1").content("Primer comentario").build(),
            comentario.builder().publicationId(1L).userId(2L).username("usuario2").content("Segundo comentario").build(),
            comentario.builder().publicationId(2L).userId(3L).username("usuario3").content("Comentario en otra publicación").build()
        );
        repository.saveAll(comentarios);
        
    }
}
