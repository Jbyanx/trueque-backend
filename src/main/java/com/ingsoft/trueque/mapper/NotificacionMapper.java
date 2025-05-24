package com.ingsoft.trueque.mapper;

import com.ingsoft.trueque.dto.request.SaveNotificacion;
import com.ingsoft.trueque.dto.response.GetNotificacion;
import com.ingsoft.trueque.model.Notificacion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificacionMapper {

    Notificacion toNotificacion(SaveNotificacion saveNotificacion);

    @Mapping(target = "fecha", source = "fechaDeCreacion")
    @Mapping(target = "id_receptor", source = "receptor.id")
    GetNotificacion toGetNotificacion(Notificacion notificacion);

    List<Notificacion> toNotificacionList(List<SaveNotificacion> saveNotificacionList);
    List<GetNotificacion> toGetNotificacionList(List<Notificacion> notificacionList);
}
