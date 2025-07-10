package com.example.ReputationService.repository;

import com.example.ReputationService.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {

    List<Like> findByPublicationId(Long publicationId);

    List<Like> findByCommentId(Long commentId);

    boolean existsByUserIdAndPublicationId(Long userId, Long publicationId);

    boolean existsByUserIdAndCommentId(Long userId, Long commentId);
}
