package com.example.CommentService.service;

import com.example.CommentService.model.Comentario;
import com.example.CommentService.repository.ComentarioRepository;
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

class ComentarioServiceTest {

    @Mock
    private ComentarioRepository comentarioRepository;

    @InjectMocks
    private ComentarioService comentarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_deberiaGuardarYRetornarComentario() {
        Comentario comentario = Comentario.builder().content("comentario de prueba").build();
        when(comentarioRepository.save(comentario)).thenReturn(comentario);

        Comentario resultado = comentarioService.save(comentario);

        assertNotNull(resultado);
        assertEquals("comentario de prueba", resultado.getContent());
    }

    @Test
    void findById_deberiaRetornarOptional() {
        Comentario comentario = Comentario.builder().id(1L).build();
        when(comentarioRepository.findById(1L)).thenReturn(Optional.of(comentario));

        Optional<Comentario> resultado = comentarioService.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void findByPublication_deberiaRetornarLista() {
        when(comentarioRepository.findByPublicationId(99L)).thenReturn(Arrays.asList(new Comentario(), new Comentario()));

        List<Comentario> resultado = comentarioService.findByPublication(99L);

        assertEquals(2, resultado.size());
    }

    @Test
    void findAll_deberiaRetornarTodosLosComentarios() {
        when(comentarioRepository.findAll()).thenReturn(Arrays.asList(new Comentario(), new Comentario(), new Comentario()));

        List<Comentario> resultado = comentarioService.findAll();

        assertEquals(3, resultado.size());
    }

    @Test
    void delete_deberiaEliminarComentarioPorId() {
        comentarioService.delete(10L);
        verify(comentarioRepository, times(1)).deleteById(10L);
    }
}
