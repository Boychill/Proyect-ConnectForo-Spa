package com.example.ReputationService.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ReputationService.model.Like;
import com.example.ReputationService.repository.LikeRepository;

@Service
public class ReputationService {

    @Autowired
    private LikeRepository likeRepository;

    // Agregar un "me gusta" a una publicación
    public Like addLikeToPublication(Long userId, Long publicationId) {
        if (likeRepository.existsByUserIdAndPublicationId(userId, publicationId)) {
            throw new RuntimeException("El usuario ya ha dado un me gusta a esta publicación");
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setPublicationId(publicationId);  // Asociar el like a la publicación
        return likeRepository.save(like);
    }

    // Agregar un "me gusta" a un comentario
    public Like addLikeToComment(Long userId, Long commentId) {
        if (likeRepository.existsByUserIdAndCommentId(userId, commentId)) {
            throw new RuntimeException("El usuario ya ha dado un me gusta a este comentario");
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setCommentId(commentId);  // Asociar el like al comentario
        return likeRepository.save(like);
    }

    // Contar los "me gusta" de una publicación
    public long countLikesForPublication(Long publicationId) {
        return likeRepository.findByPublicationId(publicationId).size();
    }

    // Contar los "me gusta" de un comentario
    public long countLikesForComment(Long commentId) {
        return likeRepository.findByCommentId(commentId).size();
    }
}
