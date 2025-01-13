package com.elice.websocket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String nickname;
    String title;
    String content;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;
    LocalDateTime deletedTime;
    @OneToMany(mappedBy = "user_id")
    List<User> users;
    @OneToMany(mappedBy = "board")
    private List<Comment> comments;
}
