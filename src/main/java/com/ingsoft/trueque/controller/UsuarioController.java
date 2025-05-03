package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/{idUsuario}/articulos")
    public ResponseEntity<Page<GetArticulo>> getArticulosByUsuario(@PathVariable Long idUsuario,
                                                                   @RequestParam(required = false) EstadoArticulo estadoArticulo,
                                                                   Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getArticulosByUsuario(idUsuario, estadoArticulo, pageable));
    }

    @GetMapping
    public ResponseEntity<Page<GetUsuario>> getAllUsuarios(Pageable pageable){
        return ResponseEntity.ok(usuarioService.getAllUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsuario> getUsuarioById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUsuario> updateUsuario(@PathVariable Long id,
                                                      @ModelAttribute @Valid UpdateUsuario usuario){
        return ResponseEntity.ok(usuarioService.updateUsuarioById(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }
}
