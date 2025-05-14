package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveReporte;
import com.ingsoft.trueque.dto.response.GetReporte;
import com.ingsoft.trueque.dto.response.PlataformaReporteResumen;
import com.ingsoft.trueque.service.ReporteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {
    private final ReporteService reporteService;

    @GetMapping
    public ResponseEntity<Page<GetReporte>> getAllReportes(Pageable pageable){
        return ResponseEntity.ok(reporteService.getAllReportes(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetReporte> getReporteById(@PathVariable Long id){
        return ResponseEntity.ok(reporteService.getReporteById(id));
    }

    @PostMapping("/articulos/{idArticulo}")
    public ResponseEntity<GetReporte> saveReporte(@PathVariable Long idArticulo , @ModelAttribute @Valid SaveReporte reporte){
        GetReporte reporteSaved = reporteService.saveReporte(idArticulo, reporte);
        URI createdReporte = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(reporteSaved.id())
                .toUri();
        return ResponseEntity.created(createdReporte).body(reporteSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetReporte> updateReporte(@PathVariable Long id,
                                                      @ModelAttribute @Valid SaveReporte reporte){
        return ResponseEntity.ok(reporteService.updateReporteById(id, reporte));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id){
        reporteService.deleteReporteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/reporte-de-actividad")
    public ResponseEntity<PlataformaReporteResumen> getReporteDeActividad(){
        return ResponseEntity.ok(reporteService.getResumenActividad());
    }

    @PutMapping("/{idReporte}/eliminar-articulo")
    public ResponseEntity<String> putString(@PathVariable Long idReporte ) {
        return ResponseEntity.ok(reporteService.eliminarArticuloReportado(idReporte));
    }

}
