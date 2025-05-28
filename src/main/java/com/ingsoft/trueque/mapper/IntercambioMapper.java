package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambio;
import com.ingsoft.trueque.dto.response.GetIntercambioSimple;
import com.ingsoft.trueque.model.Intercambio;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IntercambioMapper {
    @Mapping(target = "usuarioUno", source = "usuarioUno.id")
    @Mapping(target = "telefonoUsuarioUno", source = "usuarioUno.telefono")
    @Mapping(target = "usuarioDos", source = "usuarioDos.id")
    @Mapping(target = "telefonoUsuarioDos", source = "usuarioDos.telefono")
    @Mapping(target = "articuloUno", source = "articuloUno.id")
    @Mapping(target = "articuloDos", source = "articuloDos.id")
    @Mapping(target = "idIntercambioPadre", source = "intercambioPadre.id")
    GetIntercambio toGetIntercambio(Intercambio intercambio);


    @Mapping(target = "usuarioDos.id", source = "idUsuarioDos")
    @Mapping(target = "articuloUno.id", source = "idArticuloUno")
    @Mapping(target = "articuloDos.id", source = "idArticuloDos")
    //@Mapping(target = "intercambioPadre.id", source = "idIntercambioPadre")
    Intercambio toIntercambio(SaveIntercambio saveIntercambio);

    @Mapping(target = "usuarioUno", source = "usuarioUno.id")
    @Mapping(target = "usuarioDos", source = "usuarioDos.id")
    @Mapping(target = "articuloUno", source = "articuloUno.id")
    @Mapping(target = "articuloDos", source = "articuloDos.id")
    GetIntercambioSimple toGetIntercambioSimple(Intercambio intercambio);

    List<GetIntercambio> toGetIntercambioList(List<Intercambio> intercambioList);
    List<Intercambio> toIntercambioList(List<SaveIntercambio> saveIntercambioList);
}
