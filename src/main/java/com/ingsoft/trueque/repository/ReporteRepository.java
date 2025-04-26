package com.ingsoft.trueque.repository;

import com.ingsoft.trueque.model.Reporte;
import com.ingsoft.trueque.model.util.EstadoReporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    Page<Reporte> findAllByEstado(EstadoReporte estado, Pageable pageable);
    Page<Reporte> findAllByUsuarioNombre(String nombre, Pageable pageable);
}
