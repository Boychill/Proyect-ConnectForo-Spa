package com.example.MediaService.service;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.MediaService.model.Media;
import com.example.MediaService.repository.MediaRepository;

@Service
public class MediaService {

    private final MediaRepository mediaRepository;

    @Value("${media.upload.path}")
    private String uploadDir;

    public MediaService(MediaRepository mediaRepository) {
        this.mediaRepository = mediaRepository;
    }
    
    public List<Media> buscarPorReferencia(String tipo, Long id) {
        return mediaRepository.findByReferenciaTipoAndReferenciaId(tipo, id);
    }
    public Media guardar(MultipartFile archivo, String referenciaTipo, Long referenciaId) throws IOException {
        String nombreArchivo = UUID.randomUUID() + "_" + archivo.getOriginalFilename();
        File destino = new File(uploadDir + "/" + nombreArchivo);
        archivo.transferTo(destino);

        Media media = Media.builder()
                .nombre(nombreArchivo)
                .url("/media/" + nombreArchivo)
                .tipo(archivo.getContentType())
                .tamaño(archivo.getSize())
                .referenciaTipo(referenciaTipo)
                .referenciaId(referenciaId)
                .fechaCreacion(new Date())
                .build();

        return mediaRepository.save(media);
    }

    public boolean eliminarPorId(Long id) {
        if (mediaRepository.existsById(id)) {
            mediaRepository.deleteById(id);
            return true;
        }
        return false;
    }

}