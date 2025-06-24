package com.example.PublicationService.controller;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.PublicationService.Client.MediaClient;
import com.example.PublicationService.model.Publicacion;
import com.example.PublicationService.service.PublicacionService;

@RestController
@RequestMapping("/publicaciones")
public class PublicacionController {

    @Autowired
    private PublicacionService service;

    @Autowired
    private MediaClient mediaClient;

    @PostMapping
    public ResponseEntity<?> crear(@RequestPart("publicacion") Publicacion p,
                                   @RequestPart(value = "archivo", required = false) MultipartFile archivo,
                                   @RequestHeader("X-User-Id") String userId,
                                   @RequestHeader("X-Username") String username) {
        if (p.getTitulo() == null || p.getTitulo().isBlank() || p.getContenido() == null || p.getContenido().isBlank()) {
            return ResponseEntity.badRequest().body("El título y contenido no pueden estar vacíos.");
        }
        p.setIdUsuario(Long.parseLong(userId));
        p.setUsername(username);

        // Subir imagen asociada a la publicación si se incluye un archivo
        if (archivo != null && !archivo.isEmpty()) {
            Long mediaId = mediaClient.uploadMedia(archivo);
            p.setMediaId(mediaId);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(p));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id,
                                        @RequestBody Publicacion p,
                                        @RequestHeader("X-User-Id") String userId,
                                        @RequestHeader("X-Rol") String rol) {
        return service.buscarPorId(id).map(pub -> {
            if (!pub.getIdUsuario().equals(Long.parseLong(userId)) && !(rol.equals("MODERADOR") || rol.equals("ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes editar esta publicación");
            }
            pub.setTitulo(p.getTitulo());
            pub.setContenido(p.getContenido());
            return ResponseEntity.ok(service.guardar(pub));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id,
                                      @RequestHeader("X-User-Id") String userId,
                                      @RequestHeader("X-Rol") String rol) {
        return service.buscarPorId(id).map(pub -> {
            if (!pub.getIdUsuario().equals(Long.parseLong(userId)) && !(rol.equals("MODERADOR") || rol.equals("ADMIN"))) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes eliminar esta publicación");
            }
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/foro/{idForo}")
    public ResponseEntity<List<Publicacion>> listarPorForo(@PathVariable Long idForo) {
        return ResponseEntity.ok(service.listarPorForo(idForo));
    }

    @GetMapping
    public ResponseEntity<List<Publicacion>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    
}
