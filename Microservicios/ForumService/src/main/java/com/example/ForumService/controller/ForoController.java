package com.example.ForumService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.ForumService.client.CategoriaClient;
import com.example.ForumService.model.Categoria;
import com.example.ForumService.model.Foro;
import com.example.ForumService.service.ForoService;



@RestController
@RequestMapping("/foros")
public class ForoController {

    @Autowired
    private ForoService foroService;

    @Autowired
    private CategoriaClient categoryClient;

    @PostMapping
    public ResponseEntity<?> crearForo(@RequestBody Foro foroInput,
                                       @RequestHeader("X-User-Id") String userId,
                                       @RequestHeader("X-Username") String username,
                                       @RequestHeader("X-Rol") String rol) {

        if (!rol.equals("ADMIN") && !rol.equals("USUARIO")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para crear foros.");
        }

        ResponseEntity<Categoria> categoriaResponse = categoryClient.getCategoriaById(foroInput.getIdCategoria());
        if (!categoriaResponse.getStatusCode().is2xxSuccessful() || categoriaResponse.getBody() == null) {
            return ResponseEntity.badRequest().body("Categoría no válida");
        }

        Categoria categoria = categoriaResponse.getBody();

        Foro foro = Foro.builder()
                .titulo(foroInput.getTitulo())
                .descripcion(foroInput.getDescripcion())
                .idCategoria(foroInput.getIdCategoria())
                .idUsuario(Long.parseLong(userId))
                .username(username)
                .categoriaNombre(categoria.getNombre())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(foroService.guardar(foro));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarForo(@PathVariable Long id,
                                            @RequestBody Foro foroInput,
                                            @RequestHeader("X-Rol") String rol) {
        if (!rol.equals("ADMIN") && !rol.equals("MODERADOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para modificar foros.");
        }

        return foroService.buscarPorId(id)
                .map(foroExistente -> {
                    foroExistente.setTitulo(foroInput.getTitulo());
                    foroExistente.setDescripcion(foroInput.getDescripcion());
                    return ResponseEntity.ok(foroService.guardar(foroExistente));
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarForo(@PathVariable Long id,
                                          @RequestHeader("X-Rol") String rol) {
        if (!rol.equals("ADMIN") && !rol.equals("MODERADOR")) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No autorizado para eliminar foros.");
        }

        return foroService.buscarPorId(id)
                .map(foro -> {
                    foroService.eliminar(id);
                    return ResponseEntity.noContent().build();
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoriaId}")
    public ResponseEntity<List<Foro>> obtenerPorCategoria(@PathVariable Long categoriaId) {
        List<Foro> foros = foroService.listarPorCategoria(categoriaId);

        for (Foro foro : foros) {
            try {
                ResponseEntity<Categoria> res = categoryClient.getCategoriaById(foro.getIdCategoria());
                if (res.getStatusCode().is2xxSuccessful()) {
                    foro.setCategoriaNombre(res.getBody().getNombre());
                }
            } catch (Exception ignored) {}
        }

        return ResponseEntity.ok(foros);
    }

    @GetMapping
    public ResponseEntity<List<Foro>> listarTodos() {
        List<Foro> foros = foroService.listarTodos();

        for (Foro foro : foros) {
            try {
                ResponseEntity<Categoria> res = categoryClient.getCategoriaById(foro.getIdCategoria());
                if (res.getStatusCode().is2xxSuccessful()) {
                    foro.setCategoriaNombre(res.getBody().getNombre());
                }
            } catch (Exception ignored) {}
        }

        return ResponseEntity.ok(foros);
    }
}
