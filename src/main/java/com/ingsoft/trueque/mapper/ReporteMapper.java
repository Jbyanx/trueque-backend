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

    @Mapping(target = "fecha", source = "fechaReporte")
    @Mapping(target = "idArticulo", source = "articulo.id")
    @Mapping(target = "idAutor", source = "usuario.id")
    GetReporte toGetReporte(Reporte reporte);

    Reporte toReporte(SaveReporte saveReporte);

    List<GetReporte> toGetReporteList(List<Reporte> reportes);
    List<Reporte> toReporteList(List<SaveCategoria> saveCategorias);
}
