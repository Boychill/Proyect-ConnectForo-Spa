package com.example.ModerationService.controller;

import com.example.ModerationService.model.Moderacion;
import com.example.ModerationService.service.ModeracionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/moderaciones")
public class ModeracionController {

    private final ModeracionService service;

    public ModeracionController(ModeracionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Moderacion>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Moderacion> obtener(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/tickets")
    public ResponseEntity<?> crearTicketModeracion(@RequestBody Moderacion moderacion) {
        if (service.findByIdReporte(moderacion.getIdReporte()).isPresent()) {
            return ResponseEntity.status(409).body("Ya existe una moderación para este reporte.");
        }

        if (moderacion.getEstado() == null || moderacion.getEstado().isBlank()) {
            moderacion.setEstado("pendiente");
        }

        moderacion.setFechaRevision(LocalDate.now().toString());
        return ResponseEntity.ok(service.save(moderacion));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
