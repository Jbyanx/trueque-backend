package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveResenha;
import com.ingsoft.trueque.dto.response.GetResenha;
import com.ingsoft.trueque.service.ResenhaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/resenhas")
@RequiredArgsConstructor
@Tag(
        name = "Reseñas",
        description = "Rest controller para las reseñas"
)
public class ResenhaController {
    private final ResenhaService resenhaService;

    @GetMapping
    public ResponseEntity<Page<GetResenha>> getAllResenhas(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(resenhaService.getAllResenhas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetResenha> getResenhaById(@Parameter @PathVariable Long id){
        return ResponseEntity.ok(resenhaService.getResenhaById(id));
    }

    @PostMapping("/intercambios/{idIntercambio}")
    public ResponseEntity<GetResenha> saveResenha(@ParameterObject @ModelAttribute @Valid SaveResenha resenha, @Parameter @PathVariable Long idIntercambio){
        GetResenha resenhaSaved = resenhaService.saveResenha(idIntercambio, resenha);
        URI createdResenha = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resenhaSaved.id())
                .toUri();
        return ResponseEntity.created(createdResenha).body(resenhaSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetResenha> updateResenha(@Parameter @PathVariable Long id,
                                                      @ParameterObject @ModelAttribute @Valid SaveResenha resenha){
        return ResponseEntity.ok(resenhaService.updateResenhaById(id, resenha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResenha(@Parameter @PathVariable Long id){
        resenhaService.deleteResenhaById(id);
        return ResponseEntity.noContent().build();
    }
}
