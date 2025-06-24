package com.example.CommentService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.CommentService.client.ReputationClient;
import com.example.CommentService.model.Comentario;
import com.example.CommentService.service.ComentarioService;

@RestController
@RequestMapping("/comentarios")
public class ComentariosController {

    @Autowired
    private ComentarioService service;

    @Autowired
    private ReputationClient reputationClient;

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Comentario comentario,
                                   @RequestHeader("X-User-Id") String userId,
                                   @RequestHeader("X-Username") String username) {
                                    comentario.setUserId(Long.parseLong(userId));
                                    comentario.setUsername(username);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(comentario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody Comentario nuevo,
                                        @RequestHeader("X-User-Id") String userId,
                                        @RequestHeader("X-Rol") String rol) {
        return service.findById(id).map(c -> {
            if (!c.getUserId().equals(Long.parseLong(userId)) && !(rol.equals("MODERADOR") || rol.equals("ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes editar este comentario");
            }
            c.setContent(nuevo.getContent());
            return ResponseEntity.ok(service.save(c));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      @RequestHeader("X-User-Id") String userId,
                                      @RequestHeader("X-Rol") String rol) {
        return service.findById(id).map(c -> {
            if (!c.getUserId().equals(Long.parseLong(userId)) && !(rol.equals("MODERADOR") || rol.equals("ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes eliminar este comentario");
            }
            service.delete(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/publicacion/{id}")
    public ResponseEntity<List<Comentario>> listarPorPublicacion(@PathVariable Long id) {
        List<Comentario> comentarios = service.findByPublication(id);
        for (Comentario c : comentarios) {
            int likes = (int) reputationClient.contarLikesComentario(c.getId());
            c.setLikes(likes);
        }
        return ResponseEntity.ok(comentarios);
    }

    @GetMapping
    public ResponseEntity<List<Comentario>> listarTodos() {
        List<Comentario> comentarios = service.findAll();
        for (Comentario c : comentarios) {
            int likes = (int) reputationClient.contarLikesComentario(c.getId());
            c.setLikes(likes);
        }
        return ResponseEntity.ok(comentarios);
    }
}
