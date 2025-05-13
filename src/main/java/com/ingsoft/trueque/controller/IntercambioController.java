package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.model.util.EstadoIntercambio;
import com.ingsoft.trueque.service.IntercambioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

    /***
     * Todos los intercambios con todos los estados
     * @param pageable
     * @return
     */
    @GetMapping
    public ResponseEntity<Page<GetIntercambio>> getAllIntercambios(Pageable pageable){
        return ResponseEntity.ok(intercambioService.getAllIntercambios(pageable));
    }

    /***
     * obtener un intercambio teniendo su id
     * @param id
     * @return intercambios con cualquier estado
     */
    @GetMapping("/{id}")
    public ResponseEntity<GetIntercambio> getIntercambioById(@PathVariable Long id){
        return ResponseEntity.ok(intercambioService.getIntercambioById(id));
    }

    /***ADMINS
     * Mediante esta funcion se consultan los intercambios por idUsuario, y con el parametro EstadoIntercambio
     * enviamos que estados queremos consultar, si el parametro no se envia los trae todos
     */

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<List<GetIntercambio>> getIntercambiosByUsuarioIdAndEstado(@PathVariable Long usuarioId,
                                                                    @RequestParam(required = false) EstadoIntercambio estado){
        return ResponseEntity.ok(intercambioService.getIntercambiosByUsuarioIdAndEstado(usuarioId, estado));
    }

    @GetMapping("/todos-mis-intercambios")
    public ResponseEntity<Page<GetIntercambio>> obtenerTodosMisIntercambios(Pageable pageable){
        return ResponseEntity.ok(intercambioService.getMisIntercambios(pageable));
    }
    @GetMapping("/mis-intercambios-exitosos")
    public ResponseEntity<Page<GetIntercambio>> obtenerMisIntercambiosExitosos(Pageable pageable){
        return ResponseEntity.ok(intercambioService.getMisIntercambiosExitosos(pageable));
    }

    @PostMapping
    public ResponseEntity<GetIntercambio> solicitarIntercambio(@ModelAttribute @Valid SaveIntercambio intercambio){
        GetIntercambio intercambioSaved = intercambioService.solicitarIntercambio(intercambio);
        URI createdIntercambio = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(intercambioSaved.id())
                .toUri();
        return ResponseEntity.created(createdIntercambio).body(intercambioSaved);
    }

    @PatchMapping("/{intercambioId}/aceptar")
    public ResponseEntity<GetIntercambio> acceptIntercambio(@PathVariable Long intercambioId){
        GetIntercambio intercambioAceptado = intercambioService.aceptarIntercambio(intercambioId);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/intercambios/{id}")
                .buildAndExpand(intercambioId)
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(intercambioAceptado);
    }

    @PatchMapping("/{intercambioId}/rechazar")
    public ResponseEntity<GetIntercambio> rejectIntercambio(@PathVariable Long intercambioId){
        GetIntercambio intercambioRechazado = intercambioService.rechazarIntercambio(intercambioId);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/intercambios/{id}")
                .buildAndExpand(intercambioId)
                .toUri();
        return ResponseEntity.status(HttpStatus.OK).location(location).body(intercambioRechazado);
    }

    @PatchMapping("/{intercambioId}/cancelar")
    public ResponseEntity<GetIntercambio> cancelIntercambio(@PathVariable Long intercambioId){
        GetIntercambio intercambioCancelado = intercambioService.cancelarIntercambio(intercambioId);
        return ResponseEntity.ok(intercambioCancelado);
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
