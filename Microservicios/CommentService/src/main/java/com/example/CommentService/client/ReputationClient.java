package com.example.CommentService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.CommentService.model.Like;

@FeignClient(name = "reputation-service")
public interface ReputationClient {

    @PostMapping("/like/comment/{commentId}")
    ResponseEntity<Like> addLikeToComment(@RequestParam("userId") Long userId,
                                          @PathVariable("commentId") Long commentId);

    @GetMapping("/count/comment/{commentId}")
    long contarLikesComentario(@PathVariable("commentId") Long commentId);
}
