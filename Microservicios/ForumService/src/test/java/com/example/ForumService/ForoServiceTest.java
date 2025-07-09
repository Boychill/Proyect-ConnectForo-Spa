package com.example.ForumService;

import com.example.ForumService.model.Foro;
import com.example.ForumService.repository.ForoRepository;
import com.example.ForumService.service.ForoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ForoServiceTest {

    @Mock
    private ForoRepository foroRepository;

    @InjectMocks
    private ForoService foroService;

    private Foro foro;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        foro = Foro.builder()
                .id(1L)
                .titulo("Título de prueba")
                .descripcion("Descripción de prueba")
                .idCategoria(2L)
                .idUsuario(10L)
                .username("usuario_test")
                .build();
    }

    @Test
    void testGuardar() {
        when(foroRepository.save(foro)).thenReturn(foro);
        Foro guardado = foroService.guardar(foro);
        assertEquals(foro.getTitulo(), guardado.getTitulo());
    }

    @Test
    void testBuscarPorId() {
        when(foroRepository.findById(1L)).thenReturn(Optional.of(foro));
        Optional<Foro> resultado = foroService.buscarPorId(1L);
        assertTrue(resultado.isPresent());
        assertEquals("Título de prueba", resultado.get().getTitulo());
    }

    @Test
    void testListarTodos() {
        when(foroRepository.findAll()).thenReturn(List.of(foro));
        List<Foro> foros = foroService.listarTodos();
        assertEquals(1, foros.size());
    }

    @Test
    void testListarPorCategoria() {
        when(foroRepository.findByCategoriaId(2L)).thenReturn(List.of(foro));
        List<Foro> resultado = foroService.listarPorCategoria(2L);
        assertEquals(1, resultado.size());
    }

    @Test
    void testEliminar() {
        foroService.eliminar(1L);
        verify(foroRepository, times(1)).deleteById(1L);
    }
}
