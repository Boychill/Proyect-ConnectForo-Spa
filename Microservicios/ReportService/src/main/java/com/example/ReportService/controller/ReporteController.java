package com.example.ReportService.controller;

import com.example.ReportService.model.Reporte;
import com.example.ReportService.service.ReporteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reportes")
public class ReporteController {

    private final ReporteService service;

    public ReporteController(ReporteService service) {
        this.service = service;
    }

    @PostMapping("/crear")
    public ResponseEntity<Reporte> crear(@RequestBody Reporte reporte) {
        return ResponseEntity.ok(service.crearReporte(reporte));
    }
}
