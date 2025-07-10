package com.example.CategoryService;

import com.example.CategoryService.model.Categoria;
import com.example.CategoryService.repository.CategoriaRepository;
import com.example.CategoryService.service.CategoriaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoriaServiceTest {

    @Mock
    private CategoriaRepository repository;

    @InjectMocks
    private CategoriaService service;

    private Categoria categoria;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        categoria = Categoria.builder().id(1L).nombre("Tecnología").build();
    }

    @Test
    void testCrearCategoria() {
        when(repository.findByNombre("Tecnología")).thenReturn(Optional.empty());
        when(repository.save(any(Categoria.class))).thenReturn(categoria);

        Categoria result = service.crearCategoria(categoria);
        assertEquals("Tecnología", result.getNombre());
    }

    @Test
    void testCrearCategoriaDuplicada() {
        when(repository.findByNombre("Tecnología")).thenReturn(Optional.of(categoria));
        assertThrows(ResponseStatusException.class, () -> service.crearCategoria(categoria));
    }

    @Test
    void testActualizarCategoriaExistente() {
        when(repository.findById(1L)).thenReturn(Optional.of(categoria));
        when(repository.save(any(Categoria.class)))
            .thenAnswer(invocation -> invocation.getArgument(0));

        Categoria nueva = Categoria.builder().nombre("Ciencia").build();

        Categoria actualizada = service.actualizarCategoria(1L, nueva);
        assertEquals("Ciencia", actualizada.getNombre());
    }

    @Test
    void testActualizarCategoriaNoExistente() {
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> service.actualizarCategoria(1L, categoria));
    }
}