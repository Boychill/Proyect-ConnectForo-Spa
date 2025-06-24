package com.example.MediaService.repository;

import com.example.MediaService.model.Media;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MediaRepository extends JpaRepository<Media, Long> {
    List<Media> findByReferenciaTipoAndReferenciaId(String referenciaTipo, Long referenciaId);
    
}
