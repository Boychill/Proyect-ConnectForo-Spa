package com.example.CommentService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.CommentService.model.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
    List<Comentario> findByPublicationId(Long publicationId);
}
