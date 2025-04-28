package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveCategoria;
import com.ingsoft.trueque.dto.response.GetCategoria;
import com.ingsoft.trueque.exception.CategoriaNotFoundException;
import com.ingsoft.trueque.mapper.CategoriaMapper;
import com.ingsoft.trueque.model.Categoria;
import com.ingsoft.trueque.repository.CategoriaRepository;
import com.ingsoft.trueque.service.CategoriaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoriaServiceImpl implements CategoriaService {
    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    @Override
    public List<GetCategoria> getAllCategorias(Pageable pageable) {
        return categoriaMapper.toGetCategoriaList(categoriaRepository.findAll(pageable).getContent());
    }

    @Override
    public GetCategoria getCategoriaById(Long id) {
        return categoriaRepository.findById(id)
                .map(categoriaMapper::toGetCategoria)
                .orElseThrow(() -> new CategoriaNotFoundException("Error al buscar la categoria con id "+id+", no se encuentra guardada en BD"));

    }

    @Override
    public GetCategoria saveCategoria(SaveCategoria categoria) {
        return categoriaMapper.toGetCategoria(
                categoriaRepository.save(
                   categoriaMapper.toCategoria(categoria)
                )
        );
    }

    @Override
    @Transactional
    public GetCategoria updateCatgoriaById(Long id, SaveCategoria categoria) {
        Categoria categoriaAtual = categoriaRepository.findById(id)
                .orElseThrow(() -> new CategoriaNotFoundException("Error al buscar la categoria con id "+id+", no se encuentra guardada en BD"));
        updateCategoria(categoriaAtual, categoria);
        return categoriaMapper.toGetCategoria(categoriaRepository.save(categoriaAtual));
    }

    private void updateCategoria(Categoria categoriaAtual, SaveCategoria categoria) {
        if(StringUtils.hasText(categoria.getNombre())){
            categoriaAtual.setNombre(categoria.getNombre());
        }
        if(StringUtils.hasText(categoria.getDescripcion())){
            categoriaAtual.setDescripcion(categoria.getDescripcion());
        }
    }

    @Override
    public void deleteCategoriaById(Long id) {
        if(categoriaRepository.existsById(id)){
            categoriaRepository.deleteById(id);
        }
    }
}
