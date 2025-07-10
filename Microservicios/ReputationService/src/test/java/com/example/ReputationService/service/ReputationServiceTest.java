package com.example.ReputationService.service;

import com.example.ReputationService.model.Like;
import com.example.ReputationService.repository.LikeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ReputationServiceTest {

    @Mock
    private LikeRepository likeRepository;

    @InjectMocks
    private ReputationService reputationService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    void addLikeToPublication_deberiaGuardarLike() {
        Long userId = 1L;
        Long pubId = 10L;

        when(likeRepository.existsByUserIdAndPublicationId(userId, pubId)).thenReturn(false);
        when(likeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Like resultado = reputationService.addLikeToPublication(userId, pubId);

        assertEquals(userId, resultado.getUserId());
        assertEquals(pubId, resultado.getPublicationId());
        assertNull(resultado.getCommentId());
        verify(likeRepository).save(any());
    }

    @Test
    void addLikeToPublication_deberiaLanzarExcepcionSiYaExiste() {
        Long userId = 1L;
        Long pubId = 10L;

        when(likeRepository.existsByUserIdAndPublicationId(userId, pubId)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            reputationService.addLikeToPublication(userId, pubId);
        });

        assertEquals("El usuario ya ha dado un me gusta a esta publicación", ex.getMessage());
    }

    @Test
    void countLikesForPublication_deberiaRetornarCantidad() {
        Long pubId = 100L;

        when(likeRepository.findByPublicationId(pubId)).thenReturn(List.of(new Like(), new Like()));

        long count = reputationService.countLikesForPublication(pubId);

        assertEquals(2, count);
    }

    @Test
    void addLikeToComment_deberiaGuardarLike() {
        Long userId = 2L;
        Long commentId = 20L;

        when(likeRepository.existsByUserIdAndCommentId(userId, commentId)).thenReturn(false);
        when(likeRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Like resultado = reputationService.addLikeToComment(userId, commentId);

        assertEquals(userId, resultado.getUserId());
        assertEquals(commentId, resultado.getCommentId());
        assertNull(resultado.getPublicationId());
    }

    @Test
    void countLikesForComment_deberiaRetornarCantidad() {
        Long commentId = 200L;

        when(likeRepository.findByCommentId(commentId)).thenReturn(Collections.singletonList(new Like()));

        long count = reputationService.countLikesForComment(commentId);

        assertEquals(1, count);
    }
}
