package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveCategoria;
import com.ingsoft.trueque.dto.response.GetCategoria;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaService {
    List<GetCategoria> getAllCategorias();
    GetCategoria getCategoriaById(Long id);
    GetCategoria saveCategoria(SaveCategoria categoria);
    GetCategoria updateCatgoriaById(Long id, SaveCategoria categoria);
    void deleteCategoriaById(Long id);
}
