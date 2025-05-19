package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveArticulo;
import com.ingsoft.trueque.dto.response.GetArticulo;
import com.ingsoft.trueque.model.Articulo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ArticuloMapper {
    @Mapping(target = "urlImagen", source = "rutaImagen")
    @Mapping(target = "idCategoria", source = "categoria.id")
    @Mapping(target = "idPropietario", source = "propietario.id")
    GetArticulo toGetArticulo(Articulo articulo);

    @Mapping(target = "categoria.id", source = "idCategoria")
    Articulo toArticulo(SaveArticulo saveArticulo);

    List<GetArticulo> toGetArticuloList(List<Articulo> articulos);
    List<Articulo> toArticuloList(List<SaveArticulo> saveArticulos);
}
