package com.elice.websocket.service;

import com.elice.websocket.dto.Notification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final SimpMessagingTemplate messagingTemplate;

    public NotificationService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    public void sendNotification(String type, String message, String sender, String receiver) {
        Notification notification = new Notification();
        notification.setType(type);
        notification.setMessage(message);
        notification.setSender(sender);
        notification.setReceiver(receiver);

        messagingTemplate.convertAndSend("/topic/notifications", notification);
    }
}
