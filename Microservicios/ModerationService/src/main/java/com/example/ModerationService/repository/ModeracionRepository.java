package com.example.ModerationService.repository;

import com.example.ModerationService.model.Moderacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ModeracionRepository extends JpaRepository<Moderacion, Long> {
    Optional<Moderacion> findByIdReporte(Long idReporte); 
}
