package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveCategoria;
import com.ingsoft.trueque.dto.request.SaveReporte;
import com.ingsoft.trueque.dto.response.GetReporte;
import com.ingsoft.trueque.model.Reporte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReporteMapper {

    @Mapping(target = "autor", source = "usuario.nombre")
    @Mapping(target = "articulo", source = "articulo.nombre")
    @Mapping(target = "fecha", source = "fechaReporte")
    GetReporte toGetReporte(Reporte reporte);


    @Mapping(target = "usuario.id", source = "idAutor")
    @Mapping(target = "articulo.id", source = "idArticulo")
    Reporte toReporte(SaveReporte saveReporte);

    List<GetReporte> toGetReporteList(List<Reporte> reportes);
    List<Reporte> toReporteList(List<SaveCategoria> saveCategorias);
}
