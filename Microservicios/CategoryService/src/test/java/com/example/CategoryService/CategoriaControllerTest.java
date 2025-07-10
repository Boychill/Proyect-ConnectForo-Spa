package com.example.CategoryService;


import com.example.CategoryService.config.SecurityConfig;
import com.example.CategoryService.controller.CategoriaController;
import com.example.CategoryService.model.Categoria;
import com.example.CategoryService.service.CategoriaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CategoriaController.class)
@Import(SecurityConfig.class)
class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService service;

    @Autowired
    private ObjectMapper objectMapper;

    @WithMockUser
    @Test
    void testListarCategorias() throws Exception {
        when(service.obtenerTodas()).thenReturn(List.of(new Categoria(1L, "Tecnología")));

        mockMvc.perform(get("/api/categorias"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].nombre").value("Tecnología"));
    }

    @WithMockUser
    @Test
    void testObtenerCategoriaPorId() throws Exception {
        Categoria categoria = new Categoria(1L, "Arte");
        when(service.obtenerPorId(1L)).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categorias/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Arte"));
    }

    @WithMockUser(roles = {"ADMIN"})
    @Test
    void testCrearCategoria() throws Exception {
        Categoria categoria = new Categoria(1L, "Cultura");
        when(service.crearCategoria(any())).thenReturn(categoria);

        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Cultura"));
    }

    @WithMockUser(username = "mod", roles = {"MODERADOR"})
    @Test
    void testActualizarCategoria() throws Exception {
        Categoria categoria = new Categoria(1L, "Antiguo");
        Categoria actualizado = new Categoria(1L, "Moderno");

        when(service.obtenerPorId(1L)).thenReturn(Optional.of(categoria));
        when(service.actualizarCategoria(Mockito.eq(1L), any())).thenReturn(actualizado);

        mockMvc.perform(put("/api/categorias/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(actualizado)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Moderno"));
    }


    @WithMockUser(roles = {"ADMIN"})
    @Test
    void testEliminarCategoria() throws Exception {
        Mockito.doNothing().when(service).eliminarCategoria(1L);

        mockMvc.perform(delete("/api/categorias/1"))
            .andExpect(status().isNoContent());
    }

    @WithMockUser
    @Test
    void testObtenerPorNombre() throws Exception {
        Categoria categoria = new Categoria(2L, "Diseño");
        when(service.obtenerPorNombre("Diseño")).thenReturn(Optional.of(categoria));

        mockMvc.perform(get("/api/categorias/nombre/Diseño"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.nombre").value("Diseño"));
    }

    @WithMockUser(roles = {"USUARIO"})
    @Test
    void testAccesoDenegadoCrearCategoria() throws Exception {
        Categoria categoria = new Categoria(99L, "Prohibida");

        mockMvc.perform(post("/api/categorias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(categoria)))
            .andExpect(status().isForbidden());
    }
}
