package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Optional<Categoria> getCategoriaById(Long id);
    Boolean existsByNombreIgnoreCase(String nombre);
}
