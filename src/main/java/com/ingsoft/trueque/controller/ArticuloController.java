package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.ArticuloFiltroRequest;
import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.model.util.EstadoArticulo;
import com.ingsoft.trueque.service.ArticuloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/articulos")
@RequiredArgsConstructor
@Tag(
        name = "Articulos",
        description = "Rest controller para los articulos"
)
public class ArticuloController {
    private final ArticuloService articuloService;

    @GetMapping
    public ResponseEntity<Page<GetArticulo>> getAllArticulosDisponibles(@ModelAttribute @ParameterObject  ArticuloFiltroRequest filtros,
                                                                        @ParameterObject Pageable pageable){
        return ResponseEntity.ok(articuloService.getAllArticulosDisponibles(filtros, pageable));
    }

    @GetMapping("/categorias/{idCategoria}")
    public ResponseEntity<Page<GetArticulo>> getArticulosByCategoria(@PathVariable Long idCategoria, Pageable pageable){
        return ResponseEntity.ok(articuloService.getArticulosByIdCategoria(idCategoria, pageable));
    }

    @GetMapping("/usuarios/{usuarioId}")
    public ResponseEntity<Page<GetArticulo>> getAllArticulosDisponiblesByusuarioId(@ParameterObject Pageable pageable, @PathVariable @Parameter Long usuarioId){
        return ResponseEntity.ok(articuloService.getAllArticulosDisponiblesByUsuarioId(pageable, usuarioId));
    }

    @GetMapping("/mis-articulos")
    public ResponseEntity<Page<GetArticulo>> obtenerMisArticulos(@ParameterObject Pageable pageable){
        return ResponseEntity.ok(articuloService.obtenerMisArticulos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetArticulo> getArticuloById(@Parameter @PathVariable Long id){
        return ResponseEntity.ok(articuloService.getArticuloById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Crear un artículo",
            description = "Crea un artículo con sus datos y una imagen",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del artículo y archivo de imagen",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = SaveArticulo.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            description = "Artículo creado exitosamente",
                            responseCode = "201",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetArticulo.class)
                            )
                    )
            }
    )
    public ResponseEntity<GetArticulo> crearArticulo(
            @RequestPart("articulo") @Valid SaveArticulo articulo,
            @RequestPart("file") MultipartFile file
    ) {
        GetArticulo articuloSaved = articuloService.saveArticulo(articulo, file);
        URI createdArticulo = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(articuloSaved.id())
                .toUri();
        return ResponseEntity.created(createdArticulo).body(articuloSaved);
    }


    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un artículo",
            description = "Actualiza un artículo con sus datos y una imagen",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Datos del artículo y archivo de imagen",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                    schema = @Schema(implementation = SaveArticulo.class)
                            )
                    }
            ),
            responses = {
                    @ApiResponse(
                            description = "Artículo actualizado exitosamente",
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = GetArticulo.class)
                            )
                    )
            }
    )
    public ResponseEntity<GetArticulo> editarArticulo(@Parameter @PathVariable Long id,
                                                      @RequestPart("articulo") @Valid SaveArticulo articulo,
                                                      @RequestPart(name = "file",required = false) MultipartFile file){
        return ResponseEntity.ok(articuloService.updateArticuloById(id, articulo, file));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> eliminarArticuloLogico(@Parameter @PathVariable Long id){
        articuloService.eliminadoLogico(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarArticuloFisico(@Parameter @PathVariable Long id){
        articuloService.deleteArticuloById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/cambiar-estado")
    public ResponseEntity<GetArticulo> cambiarEstadoArticulo(@PathVariable @Parameter Long id, @RequestParam EstadoArticulo estado){
        GetArticulo articulo = articuloService.cambiarEstadoArticulo(id, estado);
        return ResponseEntity.ok(articulo);
    }
}
