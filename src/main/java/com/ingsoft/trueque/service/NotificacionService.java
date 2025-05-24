package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.response.GetNotificacion;

import java.util.List;

public interface NotificacionService {
    GetNotificacion createNotificacion(GetNotificacion getNotificacion);

    GetNotificacion readNotificacionById(Long id);
    List<GetNotificacion> readAllNotificaciones();
    List<GetNotificacion> readAllNotificacionesByReceptorId(Long receptorId);

    void deleteNotificacionById(Long id);
}
