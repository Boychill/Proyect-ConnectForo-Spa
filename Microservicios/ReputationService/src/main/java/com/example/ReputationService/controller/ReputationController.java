package com.example.ReputationService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ReputationService.model.Like;
import com.example.ReputationService.service.ReputationService;

@RestController
@RequestMapping("/api/reputation")
public class ReputationController {

    @Autowired
    private ReputationService reputationService;

    // Endpoint para agregar un "me gusta" a una publicación
    @PostMapping("/like/publication/{publicationId}")
    public Like addLikeToPublication(@RequestParam Long userId, @PathVariable Long publicationId) {
        return reputationService.addLikeToPublication(userId, publicationId);
    }

    // Endpoint para agregar un "me gusta" a un comentario
    @PostMapping("/like/comment/{commentId}")
    public Like addLikeToComment(@RequestParam Long userId, @PathVariable Long commentId) {
        return reputationService.addLikeToComment(userId, commentId);
    }

    // Endpoint para obtener la cantidad de "me gusta" de una publicación
    @GetMapping("/count/publication/{publicationId}")
    public long countLikesForPublication(@PathVariable Long publicationId) {
        return reputationService.countLikesForPublication(publicationId);
    }

    // Endpoint para obtener la cantidad de "me gusta" de un comentario
    @GetMapping("/count/comment/{commentId}")
    public long countLikesForComment(@PathVariable Long commentId) {
        return reputationService.countLikesForComment(commentId);
    }
}
