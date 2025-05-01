package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long>, JpaSpecificationExecutor<Articulo> {

    Optional<Articulo> findByPropietarioNombre(String nombre);

}
