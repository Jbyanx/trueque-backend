package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Page<Persona> findAllByNombreContainingIgnoreCase(String nombre, Pageable pageable);
}
