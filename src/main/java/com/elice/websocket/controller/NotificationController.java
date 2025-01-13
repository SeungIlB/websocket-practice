package com.elice.websocket.controller;

import com.elice.websocket.dto.Notification;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class NotificationController {

    @MessageMapping("/notification") // 클라이언트에서 "/app/notification"로 메시지 송신
    @SendTo("/topic/notifications") // 구독한 클라이언트에게 메시지 브로드캐스트
    public Notification sendNotification(Notification notification) {
        return notification; // 클라이언트로 알림 전송
    }
}

