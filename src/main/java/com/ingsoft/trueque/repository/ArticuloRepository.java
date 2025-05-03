package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticuloRepository extends JpaRepository<Articulo, Long>, JpaSpecificationExecutor<Articulo> {

    Optional<Articulo> findByPropietarioNombre(String nombre);

    Page<Articulo> getArticulosByPropietarioId(Long idUsuario, Pageable pageable);

    Page<Articulo> getArticulosByEstado(EstadoArticulo estadoArticulo, Pageable pageable);

    @Query("""
    SELECT a
        FROM Intercambio i  JOIN i.articuloUno a
        WHERE i.estado = 'REALIZADO'
        AND (i.usuarioUno.id = :usuarioId OR i.usuarioDos.id = :usuarioId)
    UNION
        SELECT a2   FROM Intercambio i2
        JOIN i2.articuloDos a2
            WHERE i2.estado = 'REALIZADO'
    AND (i2.usuarioUno.id = :usuarioId OR i2.usuarioDos.id = :usuarioId)
    """)
    Page<Articulo> findArticulosIntercambiadosPorUsuario(Long usuarioId, Pageable pageable);

    Page<Articulo> getArticulosByPropietarioIdAndEstado(Long idUsuario, Pageable pageable, EstadoArticulo estadoArticulo);

    @Query("select a from Articulo a where a.estado = 'DISPONIBLE'")
    Page<Articulo> findAllByEstado(Specification<Articulo> spec, Pageable pageable, EstadoArticulo estadoArticulo);
}
