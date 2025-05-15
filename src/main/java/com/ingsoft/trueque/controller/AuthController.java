package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.LoginRequest;
import com.ingsoft.trueque.dto.request.SaveUsuario;
import com.ingsoft.trueque.dto.response.GetUsuarioRegistrado;
import com.ingsoft.trueque.dto.response.LoginResponse;
import com.ingsoft.trueque.mapper.UsuarioMapper;
import com.ingsoft.trueque.service.UsuarioService;
import com.ingsoft.trueque.service.impl.security.AuthService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(
        name = "Autenticacion",
        description = "Rest controller para la autenticacion"
)
public class AuthController {
    private final UsuarioService usuarioService;
    private final UsuarioMapper usuarioMapper;
    private final AuthService authService;

    @PostMapping("/registrar")
    public ResponseEntity<GetUsuarioRegistrado> registrarUsuario(@ParameterObject @ModelAttribute SaveUsuario saveUsuario) {
        GetUsuarioRegistrado usuarioSaved = usuarioMapper.toGetUsuarioRegistrado(usuarioService.saveUsuario(saveUsuario));
        URI createdUsuario = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuarioSaved.id())
                .toUri();
        return ResponseEntity.created(createdUsuario).body(usuarioSaved);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody @Valid LoginRequest authenticationRequest){

        return ResponseEntity.ok(authService.login(authenticationRequest));
    }
}
