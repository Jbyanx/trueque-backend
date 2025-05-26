package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveReporte;
import com.ingsoft.trueque.dto.response.GetReporte;
import com.ingsoft.trueque.dto.response.PlataformaReporteResumen;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReporteService {
    Page<GetReporte> getAllReportes(Pageable pageable);
    GetReporte getReporteById(Long id);
    GetReporte saveReporte(Long idArticulo, SaveReporte reporte);
    GetReporte updateReporteById(Long id, SaveReporte reporte);
    void deleteReporteById(Long id);

    String desactivarArticuloReportado(Long idReporte);
    PlataformaReporteResumen getResumenActividad();

    GetReporte descartarReporte(Long id);

    Page<GetReporte> getAllReportesActivos(Pageable pageable);
}
