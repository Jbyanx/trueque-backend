package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetPerfilUsuario;
import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.service.PersonaService;
import com.ingsoft.trueque.service.UsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
@Tag(
        name = "Personas",
        description = "Rest controller para las personas"
)
public class PersonaController {
    private final PersonaService personaService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<GetPersona>> getAllPersonas(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(personaService.getAllPersonas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPersona> getPersonaById(@Parameter @PathVariable Long id){
        return ResponseEntity.ok(personaService.getPersonaById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@Parameter @PathVariable Long id){
        personaService.getPersonaById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mi-perfil")
    public ResponseEntity<GetPerfilUsuario> getPerfilUsuario() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuarioService.getUserProfile(usuario.getId()));
    }

    @GetMapping("/perfiles/{id}")
    public ResponseEntity<GetPerfilUsuario> getPerfilUsuarioById(@Parameter @PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUserProfile(id));
    }
}
