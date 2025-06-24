package com.example.ReportService.service;

import com.example.ReportService.model.Reporte;
import com.example.ReportService.repository.ReporteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReporteService {

    private final ReporteRepository repo;

    public ReporteService(ReporteRepository repo) {
        this.repo = repo;
    }

    public Reporte crearReporte(Reporte r) {
        r.setFecha(LocalDateTime.now());
        return repo.save(r);
    }
}
