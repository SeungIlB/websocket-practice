package com.elice.websocket.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardDTO {
    String author;
    String title;
    String content;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;
    LocalDateTime deletedTime;
}
