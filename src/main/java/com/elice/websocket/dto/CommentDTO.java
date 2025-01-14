package com.elice.websocket.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDTO {
    String content;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;
    LocalDateTime deletedTime;
}
