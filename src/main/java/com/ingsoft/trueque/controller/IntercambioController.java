package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import com.ingsoft.trueque.service.IntercambioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/intercambios")
@RequiredArgsConstructor
public class IntercambioController {
    private final IntercambioService intercambioService;

    @GetMapping
    public ResponseEntity<Page<GetIntercambio>> getAllIntercambios(Pageable pageable){
        return ResponseEntity.ok(intercambioService.getAllIntercambios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetIntercambio> getIntercambioById(@PathVariable Long id){
        return ResponseEntity.ok(intercambioService.getIntercambioById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List> getIntercambiosByUsuarioIdAndEstado(@PathVariable Long usuarioId,@RequestParam EstadoIntercambio estado){
        return ResponseEntity.ok(intercambioService.getIntercambiosByUsuarioIdAndEstado(usuarioId, estado));
    }

    @PostMapping
    public ResponseEntity<GetIntercambio> saveIntercambio(@ModelAttribute @Valid SaveIntercambio intercambio){
        GetIntercambio intercambioSaved = intercambioService.saveIntercambio(intercambio);
        URI createdIntercambio = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(intercambioSaved.id())
                .toUri();
        return ResponseEntity.created(createdIntercambio).body(intercambioSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetIntercambio> updateEstado(@PathVariable Long id,
                                                      @RequestParam EstadoIntercambio estadoIntercambio){
        return ResponseEntity.ok(intercambioService.updateEstadoById(id, estadoIntercambio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntercambio(@PathVariable Long id){
        intercambioService.deleteIntercambioById(id);
        return ResponseEntity.noContent().build();
    }
}
