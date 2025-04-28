package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveResenha;
import com.ingsoft.trueque.dto.response.GetResenha;
import com.ingsoft.trueque.service.ResenhaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/resenhas")
@RequiredArgsConstructor
public class ResenhaController {
    private final ResenhaService resenhaService;

    @GetMapping
    public ResponseEntity<Page<GetResenha>> getAllResenhas(Pageable pageable){
        return ResponseEntity.ok(resenhaService.getAllResenhas(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetResenha> getResenhaById(@PathVariable Long id){
        return ResponseEntity.ok(resenhaService.getResenhaById(id));
    }

    @PostMapping
    public ResponseEntity<GetResenha> saveResenha(@ModelAttribute @Valid SaveResenha resenha){
        GetResenha resenhaSaved = resenhaService.saveResenha(resenha);
        URI createdResenha = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(resenhaSaved.id())
                .toUri();
        return ResponseEntity.created(createdResenha).body(resenhaSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetResenha> updateResenha(@PathVariable Long id,
                                                      @ModelAttribute @Valid SaveResenha resenha){
        return ResponseEntity.ok(resenhaService.updateResenhaById(id, resenha));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResenha(@PathVariable Long id){
        resenhaService.deleteResenhaById(id);
        return ResponseEntity.noContent().build();
    }
}
