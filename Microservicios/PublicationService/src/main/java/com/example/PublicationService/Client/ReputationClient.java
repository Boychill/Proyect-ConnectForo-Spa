package com.example.PublicationService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.PublicationService.model.Like;

@FeignClient(name = "reputation-service", url = "http://reputation-service/api/reputation")
public interface ReputationClient {

    // Agregar un "me gusta" a una publicación
    @PostMapping("/like/publication/{publicationId}")
    Like addLikeToPublication(@RequestParam("userId") Long userId, @PathVariable("publicationId") Long publicationId);

    // Agregar un "me gusta" a un comentario
    @PostMapping("/like/comment/{commentId}")
    Like addLikeToComment(@RequestParam("userId") Long userId, @PathVariable("commentId") Long commentId);

    // Obtener la cantidad de "me gusta" de una publicación
    @GetMapping("/count/publication/{publicationId}")
    long contarLikesPublicacion(@PathVariable("publicationId") Long publicationId);

    // Obtener la cantidad de "me gusta" de un comentario
    @GetMapping("/count/comment/{commentId}")
    long contarLikesComentario(@PathVariable("commentId") Long commentId);
}
