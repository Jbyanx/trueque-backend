package com.ingsoft.trueque.service;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public void notificar(String username, String mensaje) {
        messagingTemplate.convertAndSendToUser(username, "/queue/notificaciones", mensaje);
    }
}

