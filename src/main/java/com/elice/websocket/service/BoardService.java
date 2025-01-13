package com.elice.websocket.service;

import com.elice.websocket.dto.BoardDTO;
import com.elice.websocket.entity.Board;
import com.elice.websocket.entity.User;
import com.elice.websocket.repository.BoardRepository;
import com.elice.websocket.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public Board createBoard(BoardDTO boardDTO, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저아이디가 없거나 다릅니다."));

        Board board = Board.builder()
                .title(boardDTO.getTitle())
                .author(user.getName())
                .createdTime(LocalDateTime.now())
                .content(boardDTO.getContent())
                .user(user)
                .build();
        return boardRepository.save(board);
    }

    public Board updateBoard(Long boardId, Long userId, String updatedTitle, String updatedContent) {
        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 게시글을 찾을 수 없습니다: " + boardId));

        // 유저 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저아이디가 없거나 다릅니다."));

        // 게시글 작성자가 없으면 예외 처리
        if (board.getUser() == null) {
            throw new RuntimeException("이 게시글에는 작성자가 없습니다.");
        }

        // 현재 사용자가 작성자인지 확인
        if (!user.getId().equals(board.getUser().getId())) {
            throw new RuntimeException("당신이 작성한 게시물이 아닙니다.");
        }

        // 게시글 수정
        board.updateBoard(updatedTitle, updatedContent);

        // 수정된 게시글 저장
        return boardRepository.save(board);
    }


    public String deleteBoard(Long boardId) {
        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("해당 Id로 게시글을 찾을 수 없습니다: " + boardId));

        // 현재 사용자가 작성자인지 확인
        User authenticatedUser = userService.getAuthenticatedUser();
        if (!board.getUser().getId().equals(authenticatedUser.getId())) {
            throw new RuntimeException("당신이 작성한 게시물이 아닙니다.");
        }

        Long deletedId = board.getId();

        // 작성자가 맞으면 삭제
        boardRepository.delete(board);
        return deletedId.toString();
    }

    public List<Board> getAllBoard() {
        return boardRepository.findAll();
    }

}
