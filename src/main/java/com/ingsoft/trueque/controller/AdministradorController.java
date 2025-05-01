package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveAdministrador;
import com.ingsoft.trueque.dto.request.UpdateAdministrador;
import com.ingsoft.trueque.dto.response.GetAdministrador;
import com.ingsoft.trueque.service.AdministradorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/administradores")
@RequiredArgsConstructor
public class AdministradorController {
    private final AdministradorService administradorService;

    @GetMapping
    public ResponseEntity<Page<GetAdministrador>> getAllAdministradores(Pageable pageable) {
        return ResponseEntity.ok(administradorService.getAllAdministradores(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetAdministrador> getAdministradorById(@PathVariable Long id) {
        return ResponseEntity.ok(administradorService.getAdministradorById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetAdministrador> updateAdministrador(@PathVariable Long id,
                                                      @ModelAttribute @Valid UpdateAdministrador administrador){
        return ResponseEntity.ok(administradorService.updateAdministradorById(id, administrador));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdministrador(@PathVariable Long id){
        administradorService.deleteAdministradorById(id);
        return ResponseEntity.noContent().build();
    }
}
