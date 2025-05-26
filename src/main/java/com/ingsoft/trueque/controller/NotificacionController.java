package com.ingsoft.trueque.controller;

import com.ingsoft.trueque.dto.request.SaveNotificacion;
import com.ingsoft.trueque.dto.response.GetNotificacion;
import com.ingsoft.trueque.service.NotificacionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones")
@RequiredArgsConstructor
public class NotificacionController {
    private final NotificacionService notificacionService;

    // 🟢 Crear una notificación (usado internamente por el sistema)
    @PostMapping
    public ResponseEntity<GetNotificacion> createNotificacion(@RequestBody SaveNotificacion dto) {
        GetNotificacion notificacionCreada = notificacionService.createNotificacion(dto);
        return ResponseEntity.ok(notificacionCreada);
    }

    // 🟢 Obtener una notificación específica (receptor o admin)
    @GetMapping("/{id}")
    public ResponseEntity<GetNotificacion> getNotificacionById(@PathVariable Long id) {
        GetNotificacion notificacion = notificacionService.readNotificacionById(id);
        return ResponseEntity.ok(notificacion);
    }

    // 🟢 Obtener todas las notificaciones (solo ADMIN)
    @GetMapping
    public ResponseEntity<List<GetNotificacion>> getAllNotificaciones() {
        List<GetNotificacion> lista = notificacionService.readAllNotificaciones();
        return ResponseEntity.ok(lista);
    }

    // 🟢 Obtener mis notificaciones (admin o usuario)
    @GetMapping("/mias")
    public ResponseEntity<List<GetNotificacion>> getMyNotificaciones() {
        List<GetNotificacion> misNotificaciones = notificacionService.readAllMyNotificaciones();
        return ResponseEntity.ok(misNotificaciones);
    }

    // 🟢 Eliminar notificación (solo receptor o admin)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotificacion(@PathVariable Long id) {
        notificacionService.deleteNotificacionById(id);
        return ResponseEntity.noContent().build();
    }

    //leer una notificacion (receptor o administrador)
    @PatchMapping("/{id}/leer")
    public ResponseEntity<GetNotificacion> marcarComoLeida(@PathVariable Long id) {
        GetNotificacion actualizada = notificacionService.marcarComoLeida(id);
        return ResponseEntity.ok(actualizada);
    }

    //solo notificaciones del usuario autenticado no leidas
    @GetMapping("/mias/no-leidas")
    public ResponseEntity<List<GetNotificacion>> getMisNoLeidas() {
        return ResponseEntity.ok(notificacionService.readAllMisNoLeidas());
    }


}
