package com.example.PublicationService.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.PublicationService.model.Foro;

@FeignClient(name = "forum-service", url = "http://forum-service/api/foros")
public interface ForumClient {

    // Obtener un foro por su ID
    @GetMapping("/{forumId}")
    Foro getForumById(@PathVariable Long forumId);
}
