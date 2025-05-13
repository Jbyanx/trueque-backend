package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetPerfilUsuario;
import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.service.PersonaService;
import com.ingsoft.trueque.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;
    private final UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<GetPersona>> getAllPersonas(Pageable pageable){
        return ResponseEntity.ok(personaService.getAllPersonas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPersona> getPersonaById(@PathVariable Long id){
        return ResponseEntity.ok(personaService.getPersonaById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id){
        personaService.getPersonaById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mi-perfil")
    public ResponseEntity<GetPerfilUsuario> getPerfilUsuario() {
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(usuarioService.getUserProfile(usuario.getId()));
    }

    @GetMapping("/perfiles/{id}")
    public ResponseEntity<GetPerfilUsuario> getPerfilUsuarioById(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.getUserProfile(id));
    }
}
