package com.example.PublicationService.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.PublicationService.Client.ForumClient;
import com.example.PublicationService.Client.MediaClient;
import com.example.PublicationService.Client.ReputationClient;
import com.example.PublicationService.model.Foro;
import com.example.PublicationService.model.Publicacion;
import com.example.PublicationService.repository.PublicacionRepository;

@Service
public class PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private MediaClient mediaClient;  // Feign Client para interactuar con MediaService

    @Autowired
    private ForumClient forumClient;  // Feign Client para interactuar con ForumService

    @Autowired
    private ReputationClient reputationClient;  // Feign Client para interactuar con ReputationService

    // Crear una nueva publicación con archivo multimedia
    public Publicacion createPublication(Long forumId, Publicacion publicacion, MultipartFile mediaFile) {
        // Consultar el foro por el forumId
        Foro foro = forumClient.getForumById(forumId);  // Llamada a ForumService para obtener el foro

        // Subir el archivo multimedia (imagen o video) a MediaService
        Long mediaId = mediaClient.uploadMedia(mediaFile);  // Llamada a MediaService para subir el archivo y obtener el mediaId

        // Asocia el mediaId con la publicación
        publicacion.setMediaId(mediaId);
        publicacion.setForumId(forumId);

        // Guardar la publicación en la base de datos
        return publicacionRepository.save(publicacion);
    }

    // Obtener el número de "me gusta" para una publicación
    public long getcontarLikesPublicacion(Long publicationId) {
        return reputationClient.contarLikesPublicacion(publicationId);  // Llamada a ReputationService
    }

    // Obtener la URL de la imagen o video asociada a una publicación
    public String getMediaUrl(Long mediaId) {
        return mediaClient.getMediaUrl(mediaId);  // Llamada a MediaService para obtener la URL del archivo
    }
}
