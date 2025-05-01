package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveReporte;
import com.ingsoft.trueque.dto.response.GetReporte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReporteService {
    Page<GetReporte> getAllReportes(Pageable pageable);
    GetReporte getReporteById(Long id);
    GetReporte saveReporte(Long idArticulo, SaveReporte reporte);
    GetReporte updateReporteById(Long id, SaveReporte reporte);
    void deleteReporteById(Long id);
}
