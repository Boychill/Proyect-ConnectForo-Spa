package com.example.ForumService.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ForumService.model.Foro;
import com.example.ForumService.service.ForoService;

@Component
public class DatosPrecargados implements CommandLineRunner {

    @Autowired
    private ForoService foroService;

    @Override
    public void run(String... args) throws Exception {
        if (foroService.listarTodos().isEmpty()) {
            foroService.guardar(Foro.builder()
                    .titulo("Primer Foro de Tecnología")
                    .descripcion("Discusión sobre avances tecnológicos")
                    .idCategoria(1L)
                    .idUsuario(101L)
                    .username("admin_user")
                    .build());

            foroService.guardar(Foro.builder()
                    .titulo("Foro de Educación")
                    .descripcion("Intercambio de ideas sobre enseñanza")
                    .idCategoria(2L)
                    .idUsuario(102L)
                    .username("educador123")
                    .build());

            foroService.guardar(Foro.builder()
                    .titulo("Foro de Seguridad")
                    .descripcion("Noticias y debates sobre ciberseguridad")
                    .idCategoria(3L)
                    .idUsuario(103L)
                    .username("mod_seguridad")
                    .build());
        }
    }
}
