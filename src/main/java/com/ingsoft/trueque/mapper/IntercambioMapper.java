package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.model.Intercambio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IntercambioMapper {
    @Mapping(target = "usuarioUno", source = "usuarioUno.nombre")
    @Mapping(target = "usuarioDos", source = "usuarioDos.nombre")
    @Mapping(target = "articuloUno", source = "articuloUno.nombre")
    @Mapping(target = "articuloDos", source = "articuloDos.nombre")
    GetIntercambio toGetIntercambio(Intercambio intercambio);

    @Mapping(target = "usuarioUno.id", source = "idUsuarioUno")
    @Mapping(target = "usuarioDos.id", source = "idUsuarioDos")
    @Mapping(target = "articuloUno.id", source = "idArticuloUno")
    @Mapping(target = "articuloDos.id", source = "idArticuloDos")
    Intercambio toIntercambio(SaveIntercambio saveIntercambio);

    List<GetIntercambio> toGetIntercambioList(List<Intercambio> intercambioList);
    List<Intercambio> toIntercambioList(List<SaveIntercambio> saveIntercambioList);
}
