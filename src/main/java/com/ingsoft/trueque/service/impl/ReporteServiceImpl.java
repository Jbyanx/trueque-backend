package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveReporte;
import com.ingsoft.trueque.dto.response.GetReporte;
import com.ingsoft.trueque.dto.response.PlataformaReporteResumen;
import com.ingsoft.trueque.exception.ArticuloNotFoundException;
import com.ingsoft.trueque.exception.ReporteNotFoundException;
import com.ingsoft.trueque.mapper.ReporteMapper;
import com.ingsoft.trueque.model.Articulo;
import com.ingsoft.trueque.model.Reporte;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.EstadoReporte;
import com.ingsoft.trueque.repository.ArticuloRepository;
import com.ingsoft.trueque.repository.IntercambioRepository;
import com.ingsoft.trueque.repository.ReporteRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {
    private final ReporteRepository reporteRepository;
    private final ReporteMapper reporteMapper;
    private final ArticuloRepository articuloRepository;
    private final UsuarioRepository usuarioRepository;
    private final IntercambioRepository intercambioRepository;

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public Page<GetReporte> getAllReportes(Pageable pageable) {
        return reporteRepository.findAll(pageable)
                .map(reporteMapper::toGetReporte);
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public GetReporte getReporteById(Long id) {
        return reporteRepository.findById(id)
                .map(reporteMapper::toGetReporte)
                .orElseThrow(() -> new ReporteNotFoundException("Error al obtener el reporte con id "+id+", no se encuentra en BD"));
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetReporte saveReporte(Long idArticulo, SaveReporte reporte) {
        Reporte reporteToSave = reporteMapper.toReporte(reporte);

        Articulo articulo = articuloRepository.findById(idArticulo)
                        .orElseThrow(() -> new ArticuloNotFoundException("Error al reportar el articulo con id "+idArticulo+", este articulo no se encuentra en BD"));

        Usuario actual = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        reporteToSave.setEstado(EstadoReporte.ACTIVO);
        reporteToSave.setArticulo(articulo);
        reporteToSave.setUsuario(actual);

        return reporteMapper.toGetReporte(
                reporteRepository.save(
                    reporteToSave
                )
        );
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public GetReporte updateReporteById(Long id, SaveReporte reporte) {
        Reporte reporteSaved = reporteRepository.findById(id)
                .orElseThrow(() -> new ReporteNotFoundException("Error al obtener el reporte con id "+id+", no se encuentra en BD"));

        if(StringUtils.hasText(reporte.getDescripcion())){
            reporteSaved.setDescripcion(reporte.getDescripcion());
        }

        return reporteMapper.toGetReporte(reporteRepository.save(reporteSaved));
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public void deleteReporteById(Long id) {
        if (reporteRepository.existsById(id)) {
            reporteRepository.deleteById(id);
        }
    }

    @Override
    public String  eliminarArticuloReportado(Long idReporte) {
        Reporte reporte = reporteRepository.findById(idReporte)
                .orElseThrow(() -> new ReporteNotFoundException("Error al eliminar el articulo reportado, Reporte no encontrado"));

        Articulo articulo = reporte.getArticulo();

        if (articulo != null) {
            articuloRepository.delete(articulo);
            // Marcar reporte como resuelto
            reporte.setEstado(EstadoReporte.RESUELTO);
            reporteRepository.save(reporte);
            return "Articulo eliminado exitosamente";
        }
        return "articulo no encontrado";
    }

    @Override
    public PlataformaReporteResumen getResumenActividad() {
        long totalUsuarios = usuarioRepository.count();
        long totalArticulosIntercambiados = intercambioRepository.count();
        long totalReportes = reporteRepository.count();
        long reportesPendientes = reporteRepository.countByEstado(EstadoReporte.ACTIVO);
        long reportesAtendidos = reporteRepository.countByEstado(EstadoReporte.RESUELTO);

        return new PlataformaReporteResumen(
                totalUsuarios,
                totalArticulosIntercambiados,
                totalReportes,
                reportesPendientes,
                reportesAtendidos
        );
    }
}
