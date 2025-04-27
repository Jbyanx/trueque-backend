package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveAdministrador;
import com.ingsoft.trueque.dto.response.GetAdministrador;
import com.ingsoft.trueque.dto.response.ReporteSimple;
import com.ingsoft.trueque.model.Administrador;
import com.ingsoft.trueque.model.Reporte;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdministradorMapper {
    @Mapping(target = "reportes", source = "reportesActivos")
    GetAdministrador toGetAdministrador(Administrador administrador);

    Administrador toAdministrador(SaveAdministrador saveAdministrador);

    @Mapping(target = "autor", source = "usuario.nombre")
    ReporteSimple toReporteSimple(Reporte reporte);

    @InheritInverseConfiguration
    Reporte toReporte(ReporteSimple reporteSimple);

    List<GetAdministrador> toGetAdministradorList(List<Administrador> administradorList);
    List<Administrador> toAdministradorList(List<GetAdministrador> getAdministradorList);

}
