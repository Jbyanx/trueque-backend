package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.ResetPasswordRequest;
import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.dto.response.GetReputacion;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.service.UsuarioService;
import com.ingsoft.trueque.service.impl.security.AuthService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@Tag(
        name = "Usuarios",
        description = "Rest controller para los usuarios"
)
public class UsuarioController {
    private final UsuarioService usuarioService;
    private final AuthService authService;

    @GetMapping("/{idUsuario}/articulos")
    public ResponseEntity<Page<GetArticulo>> getArticulosByUsuario(@Parameter @PathVariable Long idUsuario,
                                                                   @Parameter @RequestParam(required = false) EstadoArticulo estadoArticulo,
                                                                   @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(usuarioService.getArticulosByUsuario(idUsuario, estadoArticulo, pageable));
    }

    @GetMapping("/{idUsuario}/reputacion")
    public ResponseEntity<GetReputacion> obtenerReputacion(@Parameter @PathVariable Long idUsuario) {
        return ResponseEntity.ok(usuarioService.obtenerReputacionDelUsuario(idUsuario));
    }

    @GetMapping
    public ResponseEntity<Page<GetUsuario>> getAllUsuarios(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(usuarioService.getAllUsuarios(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetUsuario> getUsuarioById(@Parameter @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUsuarioById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetUsuario> updateUsuario(@Parameter @PathVariable Long id,
                                                      @ParameterObject @ModelAttribute @Valid UpdateUsuario usuario){
        return ResponseEntity.ok(usuarioService.updateUsuarioById(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@Parameter @PathVariable Long id){
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{idUsuario}/reset-password")
    public ResponseEntity<Void> resetPassword(@Parameter @PathVariable Long idUsuario,
                                              @ParameterObject @RequestBody @Valid ResetPasswordRequest request) {

        authService.resetPasswordByAdmin(idUsuario, request.newPassword());
        return ResponseEntity.noContent().build();
    }
}
