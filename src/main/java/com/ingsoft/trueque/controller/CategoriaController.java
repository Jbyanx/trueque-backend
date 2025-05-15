package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveCategoria;
import com.ingsoft.trueque.dto.response.GetCategoria;
import com.ingsoft.trueque.service.CategoriaService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(
        name = "Categorias",
        description = "Rest controller para las categorias"
)
public class CategoriaController {
    private final CategoriaService categoriaService;

    @GetMapping
    public ResponseEntity<List<GetCategoria>> getAllCategorias(){
        return ResponseEntity.ok(categoriaService.getAllCategorias());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetCategoria> getCategoriaById(@Parameter @PathVariable Long id){
        return ResponseEntity.ok(categoriaService.getCategoriaById(id));
    }

    @PostMapping
    public ResponseEntity<GetCategoria> saveCategoria(@ParameterObject @ModelAttribute @Valid SaveCategoria categoria){
        GetCategoria categoriaSaved = categoriaService.saveCategoria(categoria);
        URI createdCategoria = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(categoriaSaved.id())
                .toUri();
        return ResponseEntity.created(createdCategoria).body(categoriaSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetCategoria> updateCategoria(@Parameter @PathVariable Long id,
                                                      @ParameterObject @ModelAttribute @Valid SaveCategoria categoria){
        return ResponseEntity.ok(categoriaService.updateCatgoriaById(id, categoria));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoria(@Parameter @PathVariable Long id){
        categoriaService.deleteCategoriaById(id);
        return ResponseEntity.noContent().build();
    }
}
