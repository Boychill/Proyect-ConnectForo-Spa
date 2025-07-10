package com.example.PublicationService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "media-service", url = "http://media-service/api/media")
public interface MediaClient {

    // Subir un archivo multimedia y obtener el mediaId
    @PostMapping("/upload")
    Long uploadMedia(@RequestParam("file") MultipartFile file);

    // Obtener la URL del archivo multimedia por mediaId
    @GetMapping("/url/{mediaId}")
    String getMediaUrl(@PathVariable Long mediaId);
}