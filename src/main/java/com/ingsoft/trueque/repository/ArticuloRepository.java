package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long> {

    Page<Articulo> findAllByEstado(EstadoArticulo estado, Pageable pageable);
    Page<Articulo> findAllByCategoriaNombre(String categoria, Pageable pageable);
    Optional<Articulo> findByPropietarioNombre(String nombre);

}
