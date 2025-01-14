package com.elice.websocket.service;

import com.elice.websocket.dto.CommentDTO;
import com.elice.websocket.entity.Board;
import com.elice.websocket.entity.Comment;
import com.elice.websocket.entity.User;
import com.elice.websocket.repository.BoardRepository;
import com.elice.websocket.repository.CommentRepository;
import com.elice.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;


    public Comment createComment(CommentDTO commentDTO, Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 게시글을 찾을 수 없습니다: "));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 정보가 다릅니다.: "));

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .user(user)
                .board(board)
                .build();

        return commentRepository.save(comment);
    }

    public String deleteComment(Long commentId, Long boardId, Long userId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 게시글을 찾을 수 없습니다: "));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 정보가 다릅니다.: "));

        commentRepository.deleteById(commentId);

        return commentId.toString();
    }
}
