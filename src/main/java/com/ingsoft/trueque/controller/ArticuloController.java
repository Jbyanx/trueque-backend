package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.ArticuloFiltroRequest;
import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.service.ArticuloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/articulos")
@RequiredArgsConstructor
public class ArticuloController {
    private final ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<Page<GetArticulo>> getAllArticulosDisponibles(@ModelAttribute ArticuloFiltroRequest filtros,
                                                                        Pageable pageable){
        return ResponseEntity.ok(articuloService.getAllArticulosDisponibles(filtros, pageable));
    }

    @GetMapping("/mis-articulos")
    public ResponseEntity<Page<GetArticulo>> obtenerMisArticulos(Pageable pageable){
        return ResponseEntity.ok(articuloService.obtenerMisArticulos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticulo> getArticuloById(@PathVariable Long id){
        return ResponseEntity.ok(articuloService.getArticuloById(id));
    }

    @PostMapping
    public ResponseEntity<GetArticulo> crearArticulo(@RequestPart @Valid SaveArticulo articulo, @RequestPart MultipartFile file){
        GetArticulo articuloSaved = articuloService.saveArticulo(articulo, file);
        URI createdArticulo = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(articuloSaved.id())
                .toUri();
        return ResponseEntity.created(createdArticulo).body(articuloSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetArticulo> editarArticulo(@PathVariable Long id,
                                                      @RequestPart @Valid SaveArticulo articulo,
                                                      @RequestPart(required = false) MultipartFile file){
        return ResponseEntity.ok(articuloService.updateArticuloById(id, articulo, file));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> eliminarArticuloLogico(@PathVariable Long id){
        articuloService.eliminadoLogico(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArticuloFisico(@PathVariable Long id){
        articuloService.deleteArticuloById(id);
        return ResponseEntity.noContent().build();
    }
}
