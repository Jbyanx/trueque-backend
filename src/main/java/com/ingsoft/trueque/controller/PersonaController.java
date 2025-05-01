package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.UpdateUsuario;
import com.ingsoft.trueque.dto.response.GetPersona;
import com.ingsoft.trueque.dto.response.GetUsuario;
import com.ingsoft.trueque.service.PersonaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/personas")
@RequiredArgsConstructor
public class PersonaController {
    private final PersonaService personaService;

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
}
