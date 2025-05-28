package com.ingsoft.trueque.service.impl;

import com.ingsoft.trueque.dto.request.SaveNotificacion;
import com.ingsoft.trueque.dto.response.GetNotificacion;
import com.ingsoft.trueque.exception.AccesoNoPermitidoException;
import com.ingsoft.trueque.exception.LogicaNegocioException;
import com.ingsoft.trueque.exception.NotificacionNotFoundException;
import com.ingsoft.trueque.exception.UsuarioNotFoundException;
import com.ingsoft.trueque.mapper.NotificacionMapper;
import com.ingsoft.trueque.model.Notificacion;
import com.ingsoft.trueque.model.Persona;
import com.ingsoft.trueque.model.Usuario;
import com.ingsoft.trueque.model.util.Rol;
import com.ingsoft.trueque.repository.NotificacionRepository;
import com.ingsoft.trueque.repository.UsuarioRepository;
import com.ingsoft.trueque.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacionServiceImpl implements NotificacionService {
    private final SimpMessagingTemplate messagingTemplate;
    private final UsuarioRepository usuarioRepository;
    private final NotificacionRepository notificacionRepository;
    private final NotificacionMapper notificacionMapper;

    //esta funcion la realiza el sistema automaticamente cuando se realizan, aceptan, cancelan intercambios
    //no hay preauthorizes
    @Override
    public GetNotificacion createNotificacion(SaveNotificacion saveNotificacion) {
        Usuario receptor = usuarioRepository.getUsuarioById(saveNotificacion.id_receptor())
                        .orElseThrow(() -> new UsuarioNotFoundException("El receptor de la notificacion no existe en DB"));

        Notificacion notificacion = notificacionMapper.toNotificacion(saveNotificacion);
        notificacion.setReceptor(receptor);
        notificacion.setLeida(false);
        Notificacion guardada = notificacionRepository.save(notificacion);
        GetNotificacion dto = notificacionMapper.toGetNotificacion(guardada);

        // Notificar en tiempo real por WebSocket
        messagingTemplate.convertAndSendToUser(
                receptor.getUsername(),
                "/queue/notificaciones",
                dto
        );
        return dto;
    }

    //esta funcion si la ejecutan los ADMIN y los propietarios
    @PreAuthorize("hasRole('ADMINISTRADOR') or hasRole('USUARIO')")
    @Override
    public GetNotificacion readNotificacionById(Long id) {
        Notificacion notificacion = notificacionRepository.findNotificacionById(id)
                .orElseThrow(() -> new NotificacionNotFoundException("Esta notificacion no existe en BD"));

        Usuario actual = (Usuario) obtenerPrincipal();

        if(!notificacion.getReceptor().equals(actual) && !actual.getRol().equals(Rol.ADMINISTRADOR)){
            throw new AccesoNoPermitidoException("Solo el receptor de la notificacion y los administradores pueden leer esta notificacion");
        }

        return notificacionMapper.toGetNotificacion(notificacion);
    }

    //esta funcion si la ejecutan los ADMIN
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @Override
    public List<GetNotificacion> readAllNotificaciones() {
        return notificacionMapper.toGetNotificacionList(
                notificacionRepository.findAll()
        );
    }

    //esta funcion si la ejecutan los usuarios (USUARIO PROPIETARIO y ADMIN)
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public List<GetNotificacion> readAllMyNotificaciones() {
        Persona actual = obtenerPrincipal();

        if(actual == null){
            throw new LogicaNegocioException("No hay un usuario autenticado o falta el token");
        }

        Usuario receptor = usuarioRepository.getUsuarioById(actual.getId())
                .orElseThrow(() -> new UsuarioNotFoundException("El receptor de la notificacion no existe en DB"));


        return notificacionMapper.toGetNotificacionList(
                notificacionRepository.findAllByReceptorId(actual.getId())
        );
    }

    //esta funcion si la ejecutan los usuarios (USUARIO PROPIETARIO y ADMIN)
    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public void deleteNotificacionById(Long id) {
        Notificacion notificacion = notificacionRepository.findNotificacionById(id)
                .orElseThrow(() -> new NotificacionNotFoundException("Notificacion no existe en BD"));

        Usuario actual = (Usuario) obtenerPrincipal();

        if(!notificacion.getReceptor().getId().equals(actual.getId()) && !actual.getRol().equals(Rol.ADMINISTRADOR)){
            throw new AccesoNoPermitidoException("No tienes permiso para realizar esta accion, solo el receptor y admins");
        }
        notificacionRepository.deleteById(id);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public GetNotificacion marcarComoLeida(Long id) {
        Notificacion notificacion = notificacionRepository.findNotificacionById(id)
                .orElseThrow(() -> new NotificacionNotFoundException("No existe la notificación"));

        Usuario actual = (Usuario) obtenerPrincipal();

        if (!notificacion.getReceptor().getId().equals(actual.getId()) && !actual.getRol().equals(Rol.ADMINISTRADOR)) {
            throw new AccesoNoPermitidoException("No tienes permiso para marcar esta notificación como leída");
        }

        notificacion.setLeida(true);
        notificacionRepository.save(notificacion);

        return notificacionMapper.toGetNotificacion(notificacion);
    }

    @PreAuthorize("hasRole('USUARIO') or hasRole('ADMINISTRADOR')")
    @Override
    public List<GetNotificacion> readAllMisNoLeidas() {
        Persona actual = obtenerPrincipal();

        if (actual == null) {
            throw new LogicaNegocioException("No hay usuario autenticado");
        }

        return notificacionMapper.toGetNotificacionList(
                notificacionRepository.findAllByReceptorIdAndLeidaFalse(actual.getId())
        );
    }

    public Persona obtenerPrincipal(){
        return (Persona) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
