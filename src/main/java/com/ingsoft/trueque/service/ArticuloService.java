package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.ArticuloFiltroRequest;
import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface ArticuloService {
    Page<GetArticulo> getAllArticulosDisponibles(Pageable pageable);
    GetArticulo getArticuloById(Long id);
    GetArticulo saveArticulo(SaveArticulo articulo, MultipartFile imagen);
    GetArticulo updateArticuloById(Long id, SaveArticulo articulo, MultipartFile imagen);
    void deleteArticuloById(Long id);
    GetArticulo eliminadoLogico(Long id);
    Page<GetArticulo> obtenerMisArticulos(Pageable pageable);

    Page<GetArticulo> getAllArticulosDisponiblesByUsuarioId(Pageable pageable, Long id);

    Page<GetArticulo> getArticulosByIdCategoria(Long idCategoria, Pageable pageable);
}
