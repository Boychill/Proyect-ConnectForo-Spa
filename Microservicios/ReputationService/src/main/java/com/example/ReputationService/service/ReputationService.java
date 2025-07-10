package com.example.ReputationService.service;

import com.example.ReputationService.model.Like;
import com.example.ReputationService.repository.LikeRepository;
import org.springframework.stereotype.Service;

@Service
public class ReputationService {

    private final LikeRepository likeRepository;

    public ReputationService(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    public Like addLikeToPublication(Long userId, Long publicationId) {
        if (likeRepository.existsByUserIdAndPublicationId(userId, publicationId)) {
            throw new RuntimeException("El usuario ya ha dado un me gusta a esta publicación");
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setPublicationId(publicationId);
        return likeRepository.save(like);
    }

    public Like addLikeToComment(Long userId, Long commentId) {
        if (likeRepository.existsByUserIdAndCommentId(userId, commentId)) {
            throw new RuntimeException("El usuario ya ha dado un me gusta a este comentario");
        }

        Like like = new Like();
        like.setUserId(userId);
        like.setCommentId(commentId);
        return likeRepository.save(like);
    }

    public long countLikesForPublication(Long publicationId) {
        return likeRepository.findByPublicationId(publicationId).size();
    }

    public long countLikesForComment(Long commentId) {
        return likeRepository.findByCommentId(commentId).size();
    }
}
