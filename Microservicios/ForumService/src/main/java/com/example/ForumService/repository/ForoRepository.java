package com.example.ForumService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ForumService.model.Foro;

@Repository
public interface ForoRepository extends JpaRepository<Foro, Long> {
    // Recupera todos los foros
    List<Foro> findAll(long id);

    //Recupera foro por el idcategoria
    List<Foro> findByIdCategoria(Long idCategoria);

    // Recupera los foros por el ID de la categoría
    List<Foro> findByCategoriaId(Long categoriaId);
    
    // Recupera un foro por su ID
    Optional<Foro> findById(Long id);

    List<Foro> findByIdUsuario(Long idUsuario);
}
