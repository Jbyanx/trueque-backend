package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticuloService {
    Page<GetArticulo> getAllArticulos(Pageable pageable);
    GetArticulo getArticulosById(Long id);
    GetArticulo saveArticulo(SaveArticulo articulo);
    GetArticulo updateArticuloById(Long id, SaveArticulo articulo);
    void deleteArticuloById(Long id);
}
