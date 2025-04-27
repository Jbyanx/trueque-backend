package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<GetUsuario>> getAllUsuarios(Pageable pageable){
        return ResponseEntity.ok(usuarioService.getAllUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsuario> getUsuarioById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PostMapping
    public ResponseEntity<GetUsuario> saveUsuario(@RequestBody @Valid SaveUsuario usuario){
        GetUsuario usuarioSaved = usuarioService.saveUsuario(usuario);
        URI createdUsuario = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioSaved.id())
                .toUri();
        return ResponseEntity.created(createdUsuario).body(usuarioSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUsuario> updateUsuario(@PathVariable Long id,
                                                      @RequestBody @Valid UpdateUsuario usuario){
        return ResponseEntity.ok(usuarioService.updateUsuarioById(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }
}
