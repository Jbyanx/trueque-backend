package com.ingsoft.trueque.service;

import com.ingsoft.trueque.dto.request.SaveNotificacion;
import com.ingsoft.trueque.dto.response.GetNotificacion;

import java.util.List;

public interface NotificacionService {
    GetNotificacion createNotificacion(SaveNotificacion saveNotificacion);

    GetNotificacion readNotificacionById(Long id);
    List<GetNotificacion> readAllNotificaciones();
    List<GetNotificacion> readAllMyNotificaciones();

    void deleteNotificacionById(Long id);

    GetNotificacion marcarComoLeida(Long id);

    List<GetNotificacion> readAllMisNoLeidas();
}
