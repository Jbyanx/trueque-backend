package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<GetUsuario> register(@ModelAttribute SaveUsuario saveUsuario) {
        GetUsuario usuarioSaved = usuarioService.saveUsuario(saveUsuario);
        URI createdUsuario = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioSaved.id())
                .toUri();
        return ResponseEntity.created(createdUsuario).body(usuarioSaved);
    }
}
