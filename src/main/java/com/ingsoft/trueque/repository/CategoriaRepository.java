package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    Boolean existsByNombreIgnoreCase(String nombre);
}
