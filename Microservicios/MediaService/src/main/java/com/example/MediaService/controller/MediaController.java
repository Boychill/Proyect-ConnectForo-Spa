package com.example.MediaService.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.MediaService.model.Media;
import com.example.MediaService.service.MediaService;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private final MediaService mediaService;

    public MediaController(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Media> subir(
            @RequestParam("file") MultipartFile file,
            @RequestParam("referenciaTipo") String tipo,
            @RequestParam("referenciaId") Long id
    ) throws IOException {
        return ResponseEntity.ok(mediaService.guardar(file, tipo, id));
    }

    @GetMapping("/por-referencia")
    public ResponseEntity<List<Media>> getPorReferencia(
            @RequestParam String referenciaTipo,
            @RequestParam Long referenciaId
    ) {
        return ResponseEntity.ok(
                mediaService.buscarPorReferencia(referenciaTipo, referenciaId)
        );
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        if (mediaService.eliminarPorId(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
