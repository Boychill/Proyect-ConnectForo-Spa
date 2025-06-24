package com.example.ModerationService.service;

import com.example.ModerationService.model.Moderacion;
import com.example.ModerationService.repository.ModeracionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ModeracionService {

    private final ModeracionRepository repository;

    public ModeracionService(ModeracionRepository repository) {
        this.repository = repository;
    }

    public List<Moderacion> findAll() {
        return repository.findAll();
    }

    public Optional<Moderacion> findById(Long id) {
        return repository.findById(id);
    }

    public Optional<Moderacion> findByIdReporte(Long idReporte) {
        return repository.findByIdReporte(idReporte);
    }

    public Moderacion save(Moderacion moderacion) {
        return repository.save(moderacion);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
