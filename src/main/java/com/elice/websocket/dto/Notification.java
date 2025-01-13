package com.elice.websocket.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Notification {
    private String type; // 좋아요, 댓글, 팔로우 등
    private String message;
    private String sender;
    private String receiver;

    // Getters and Setters
}

