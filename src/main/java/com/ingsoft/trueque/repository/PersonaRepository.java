package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Persona;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Page<Persona> findAllByNombreContainingIgnoreCase(String nombre, Pageable pageable);
    Optional<Persona> findByCorreoEqualsIgnoreCase(String correo);
    Optional<Persona> getPersonaByCorreoEqualsIgnoreCase(String correo);

    Boolean existsByCorreoIgnoreCase(String correo);
}
