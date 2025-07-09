package com.example.ForumService;

import com.example.ForumService.client.CategoriaClient;
import com.example.ForumService.controller.ForoController;
import com.example.ForumService.model.Categoria;
import com.example.ForumService.model.Foro;
import com.example.ForumService.service.ForoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ForoController.class, excludeAutoConfiguration = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class
})
@AutoConfigureMockMvc(addFilters = false)
public class ForoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ForoService foroService;

    @MockBean
    private CategoriaClient categoriaClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearForo_UsuarioAutorizado() throws Exception {
        Foro input = Foro.builder().titulo("Foro Nuevo").descripcion("Contenido").idCategoria(1L).build();
        Foro saved = Foro.builder().id(1L).titulo("Foro Nuevo").descripcion("Contenido").idCategoria(1L).build();
        Categoria categoria = new Categoria(1L, "Tecnología");

        when(categoriaClient.getCategoriaById(1L)).thenReturn(ResponseEntity.ok(categoria));
        when(foroService.guardar(any())).thenReturn(saved);

        mockMvc.perform(post("/foros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
                .header("X-User-Id", "5")
                .header("X-Username", "juanito")
                .header("X-Rol", "USUARIO"))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.titulo").value("Foro Nuevo"));
    }

    @Test
    void testCrearForo_NoAutorizado() throws Exception {
        Foro input = Foro.builder().titulo("Foro No").descripcion("Denegado").idCategoria(1L).build();

        mockMvc.perform(post("/foros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(input))
                .header("X-User-Id", "5")
                .header("X-Username", "juanito")
                .header("X-Rol", "INVITADO"))
            .andExpect(status().isForbidden());
    }

    @Test
    void testActualizarForo_Admin() throws Exception {
        Foro existente = Foro.builder().id(1L).titulo("Antiguo").descripcion("Texto").build();
        Foro actualizado = Foro.builder().id(1L).titulo("Nuevo").descripcion("Actualizado").build();

        when(foroService.buscarPorId(1L)).thenReturn(Optional.of(existente));
        when(foroService.guardar(any())).thenReturn(actualizado);

        mockMvc.perform(put("/foros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado))
                .header("X-Rol", "ADMIN"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.titulo").value("Nuevo"));
    }

    @Test
    void testActualizarForo_Denegado() throws Exception {
        Foro actualizado = Foro.builder().id(1L).titulo("No").descripcion("Denegado").build();

        mockMvc.perform(put("/foros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado))
                .header("X-Rol", "USUARIO"))
            .andExpect(status().isForbidden());
    }

    @Test
    void testEliminarForo_Moderador() throws Exception {
        Foro foro = Foro.builder().id(1L).titulo("Eliminar").build();
        when(foroService.buscarPorId(1L)).thenReturn(Optional.of(foro));
        doNothing().when(foroService).eliminar(1L);

        mockMvc.perform(delete("/foros/1")
                .header("X-Rol", "MODERADOR"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testEliminarForo_Denegado() throws Exception {
        mockMvc.perform(delete("/foros/1")
                .header("X-Rol", "USUARIO"))
            .andExpect(status().isForbidden());
    }

    @Test
    void testObtenerPorCategoria() throws Exception {
        Foro foro = Foro.builder().id(1L).titulo("Foro").idCategoria(1L).build();
        when(foroService.listarPorCategoria(1L)).thenReturn(List.of(foro));
        when(categoriaClient.getCategoriaById(1L)).thenReturn(ResponseEntity.ok(new Categoria(1L, "Arte")));

        mockMvc.perform(get("/foros/categoria/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].titulo").value("Foro"));
    }

    @Test
    void testListarTodos() throws Exception {
        Foro foro = Foro.builder().id(2L).titulo("General").idCategoria(1L).build();
        when(foroService.listarTodos()).thenReturn(List.of(foro));
        when(categoriaClient.getCategoriaById(1L)).thenReturn(ResponseEntity.ok(new Categoria(1L, "Cultura")));

        mockMvc.perform(get("/foros"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].titulo").value("General"));
    }
}
