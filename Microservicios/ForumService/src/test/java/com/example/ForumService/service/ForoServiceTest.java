package com.example.ForumService.service;

import com.example.ForumService.model.Foro;
import com.example.ForumService.repository.ForoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ForoServiceTest {

    @Mock
    private ForoRepository foroRepository;

    @InjectMocks
    private ForoService foroService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardar_deberiaGuardarYRetornarForo() {
        Foro foro = new Foro();
        foro.setTitulo("Test Foro");

        when(foroRepository.save(foro)).thenReturn(foro);

        Foro resultado = foroService.guardar(foro);

        assertNotNull(resultado);
        assertEquals("Test Foro", resultado.getTitulo());
    }

    @Test
    void buscarPorId_deberiaRetornarOptional() {
        Foro foro = new Foro();
        foro.setId(1L);

        when(foroRepository.findById(1L)).thenReturn(Optional.of(foro));

        Optional<Foro> resultado = foroService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void listarTodos_deberiaRetornarLista() {
        when(foroRepository.findAll()).thenReturn(Arrays.asList(new Foro(), new Foro()));

        List<Foro> resultado = foroService.listarTodos();

        assertEquals(2, resultado.size());
    }

    @Test
    void listarPorCategoria_deberiaFiltrarPorCategoria() {
        when(foroRepository.findByCategoriaId(10L)).thenReturn(Arrays.asList(new Foro(), new Foro()));

        List<Foro> resultado = foroService.listarPorCategoria(10L);

        assertEquals(2, resultado.size());
    }

    @Test
    void eliminar_deberiaInvocarDeleteById() {
        foroService.eliminar(5L);
        verify(foroRepository, times(1)).deleteById(5L);
    }
}
