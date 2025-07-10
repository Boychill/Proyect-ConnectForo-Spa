package com.example.PublicationService.service;

import com.example.PublicationService.model.Publicacion;
import com.example.PublicationService.repository.PublicacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PublicacionServiceTest {

    @Mock
    private PublicacionRepository repository;

    @InjectMocks
    private PublicacionService service;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void guardar_deberiaGuardarYRetornarPublicacion() {
        Publicacion p = new Publicacion(null, "Título", "Contenido", 1L, 1L, 1L, "usuario");
        when(repository.save(p)).thenReturn(p);

        Publicacion resultado = service.guardar(p);

        assertEquals("Título", resultado.getTitulo());
        verify(repository, times(1)).save(p);
    }

    @Test
    void buscarPorId_deberiaRetornarPublicacionOptional() {
        Publicacion p = new Publicacion(1L, "Título", "Contenido", 1L, 1L, 1L, "usuario");
        when(repository.findById(1L)).thenReturn(Optional.of(p));

        Optional<Publicacion> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals(1L, resultado.get().getId());
    }

    @Test
    void listarPorForo_deberiaRetornarListaPublicaciones() {
        List<Publicacion> lista = Arrays.asList(
                new Publicacion(1L, "P1", "C1", 10L, 1L, 1L, "usuario"),
                new Publicacion(2L, "P2", "C2", 10L, 1L, 2L, "usuario2")
        );

        when(repository.findByForumId(10L)).thenReturn(lista);

        List<Publicacion> resultado = service.listarPorForo(10L);

        assertEquals(2, resultado.size());
        verify(repository).findByForumId(10L);
    }

    @Test
    void listarTodas_deberiaRetornarTodasLasPublicaciones() {
        List<Publicacion> lista = Arrays.asList(
                new Publicacion(1L, "P1", "C1", 10L, 1L, 1L, "usuario")
        );

        when(repository.findAll()).thenReturn(lista);

        List<Publicacion> resultado = service.listarTodas();

        assertEquals(1, resultado.size());
        verify(repository).findAll();
    }

    @Test
    void eliminar_deberiaEliminarPublicacionPorId() {
        Long id = 1L;

        service.eliminar(id);

        verify(repository).deleteById(id);
    }
}
