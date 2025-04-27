package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveCategoria;
import com.ingsoft.trueque.dto.response.GetCategoria;
import com.ingsoft.trueque.model.Categoria;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    GetCategoria toGetCategoria(Categoria categoria);

    Categoria toCategoria(SaveCategoria saveCategoria);

    List<GetCategoria> toGetCategoriaList(List<Categoria> categorias);
    List<Categoria> toCategoriaList(List<SaveCategoria> saveCategorias);
}
