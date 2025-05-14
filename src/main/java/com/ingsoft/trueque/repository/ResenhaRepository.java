package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Resenha;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResenhaRepository extends JpaRepository<Resenha, Long> {

    Page<Resenha> findAllByUsuarioCalificanteNombre(String nombre, Pageable pageable);
    Page<Resenha> findAllByPuntuacionGreaterThanEqual(Integer puntuacionMinima, Pageable pageable);

    boolean existsByIntercambioIdAndUsuarioCalificanteId(Long idIntercambio, @NotNull @Positive Long idUsuarioCalificante);
}
