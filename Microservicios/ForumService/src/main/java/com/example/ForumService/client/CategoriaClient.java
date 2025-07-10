package com.example.ForumService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.ForumService.model.Categoria;

@FeignClient(name = "categoria-service", url = "http://localhost:8082") // o nombre del servicio si usas Eureka
public interface CategoriaClient {

    @GetMapping("/categorias/{id}")
    ResponseEntity<Categoria> getCategoriaById(@PathVariable("id") Long id);
}
