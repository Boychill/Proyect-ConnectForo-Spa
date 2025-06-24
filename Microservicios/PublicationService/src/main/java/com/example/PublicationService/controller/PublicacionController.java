package com.example.PublicationService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.PublicationService.model.Publicacion;
import com.example.PublicationService.service.PublicacionService;

@RestController
@RequestMapping("/api/publications")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    // Crear una publicación con video o imagen
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping("/forum/{forumId}")
    public ResponseEntity<Publicacion> createPublication(@PathVariable Long forumId,
                                         @RequestParam("file") MultipartFile file,
                                         @RequestBody Publicacion publication) {
        try {
            // Crear la publicación, hacer la llamada al servicio de Media para subir el archivo
            Publicacion createdPublication = publicacionService.createPublication(forumId, publication, file);
            return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);  // 201 Created
        } catch (ResponseStatusException e) {
            // Manejar la excepción y devolver una respuesta adecuada
            return new ResponseEntity<>(null, e.getStatus());  // 404 Not Found si no se encuentra el foro
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);  // 400 Bad Request si ocurre otro error
        }
    }

    // Obtener todas las publicaciones
    @GetMapping
    public ResponseEntity<List<Publicacion>> getAllPublications() {
        List<Publicacion> publicaciones = publicacionService.getAllPublications();
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);  // 200 OK
    }

    // Obtener publicaciones por foro
    @GetMapping("/forum/{forumId}")
    public ResponseEntity<List<Publicacion>> getPublicationsByForum(@PathVariable Long forumId) {
        List<Publicacion> publicaciones = publicacionService.getPublicationsByForum(forumId);
        return new ResponseEntity<>(publicaciones, HttpStatus.OK);  // 200 OK
    }

    // Obtener la URL de la imagen o video asociado a una publicación
    @GetMapping("/media/{mediaId}")
    public ResponseEntity<String> getMediaUrl(@PathVariable String mediaId) {
        String mediaUrl = publicacionService.getMediaUrl(mediaId);
        return new ResponseEntity<>(mediaUrl, HttpStatus.OK);  // 200 OK
    }
}
