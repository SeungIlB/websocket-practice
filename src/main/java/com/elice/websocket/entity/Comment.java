package com.elice.websocket.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String comment;
    LocalDateTime createdTime;
    LocalDateTime updatedTime;
    LocalDateTime deletedTime;
    @OneToMany(mappedBy = "board_id")
    List<Board> boards;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")  // 댓글을 작성한 사용자
    private User user;  // 사용자와의 관계 (하나의 사용자는 여러 개의 댓글을 작성할 수 있음)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")  // 댓글이 달린 게시글
    private Board board;  // 게시글과의 관계 (하나의 게시글은 여러 개의 댓글을 가질 수 있음)

}
