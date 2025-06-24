package com.example.SettingsService.controller;

import com.example.SettingsService.model.ConfiguracionUsuario;
import com.example.SettingsService.repository.ConfiguracionUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/configuraciones")
public class ConfiguracionUsuarioController {

    @Autowired
    private ConfiguracionUsuarioRepository repository;

    @GetMapping
    public List<ConfiguracionUsuario> obtenerTodas() {
        return repository.findAll();
    }

    @PostMapping
    public ConfiguracionUsuario crear(@RequestBody ConfiguracionUsuario configuracion) {
        return repository.save(configuracion);
    }

    @PutMapping("/{id}")
    public ConfiguracionUsuario actualizar(@PathVariable Long id, @RequestBody ConfiguracionUsuario nuevaConf) {
        return repository.findById(id)
                .map(conf -> {
                    conf.setLenguajePreferido(nuevaConf.getLenguajePreferido());
                    conf.setNotificacionesHabilitadas(nuevaConf.isNotificacionesHabilitadas());
                    return repository.save(conf);
                }).orElseGet(() -> {
                    nuevaConf.setId(id);
                    return repository.save(nuevaConf);
                });
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
