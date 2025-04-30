package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.service.IntercambioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


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

    @PostMapping
    public ResponseEntity<GetIntercambio> saveIntercambio(@ModelAttribute @Valid SaveIntercambio intercambio){
        GetIntercambio intercambioSaved = intercambioService.saveIntercambio(intercambio);
        URI createdIntercambio = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(intercambioSaved.id())
                .toUri();
        return ResponseEntity.created(createdIntercambio).body(intercambioSaved);
    }

    /***
     * Este metodo solo cambia el estado que esta dentro del dto de intercambio, y el resto de
     * parametros son obligatorios pero no se modifican
     * @param id
     * @param intercambio
     * @return
     */
    @PutMapping("/{id}")
    public ResponseEntity<GetIntercambio> updateIntercambio(@PathVariable Long id,
                                                      @ModelAttribute @Valid SaveIntercambio intercambio){
        return ResponseEntity.ok(intercambioService.updateIntercambioById(id, intercambio));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIntercambio(@PathVariable Long id){
        intercambioService.deleteIntercambioById(id);
        return ResponseEntity.noContent().build();
    }
}
