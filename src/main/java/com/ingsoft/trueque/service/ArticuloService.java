package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.ArticuloFiltroRequest;
import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

public interface ArticuloService {
    Page<GetArticulo> getAllArticulos(ArticuloFiltroRequest filtros,
                                      Pageable pageable);
    GetArticulo getArticuloById(Long id);
    GetArticulo saveArticulo(SaveArticulo articulo, MultipartFile imagen);
    GetArticulo updateArticuloById(Long id, SaveArticulo articulo, MultipartFile imagen);
    void deleteArticuloById(Long id);
}
