package com.example.ReputationService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ReputationService.model.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Obtener los likes de una publicación
    List<Like> findByPublicationId(Long publicationId);

    // Obtener los likes de un comentario
    List<Like> findByCommentId(Long commentId);

    // Verificar si un usuario ha dado un "me gusta" a una publicación
    boolean existsByUserIdAndPublicationId(Long userId, Long publicationId);

    // Verificar si un usuario ha dado un "me gusta" a un comentario
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);
}
