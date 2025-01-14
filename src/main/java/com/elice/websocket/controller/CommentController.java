package com.elice.websocket.controller;

import com.elice.websocket.dto.CommentDTO;
import com.elice.websocket.entity.Comment;
import com.elice.websocket.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{boardId}/{userId}")
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO, @PathVariable Long boardId, @PathVariable Long userId){
        Comment comment = commentService.createComment(commentDTO, boardId, userId);

        return ResponseEntity.ok(comment);

    }

    @DeleteMapping("/{boardId}/{userId}/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, @PathVariable Long boardId, @PathVariable Long userId){
        String deleteCommentId = commentService.deleteComment(commentId, boardId, userId);

        return ResponseEntity.ok(deleteCommentId);
    }
}
