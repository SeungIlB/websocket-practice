package com.elice.websocket.controller;

import com.elice.websocket.dto.BoardDTO;
import com.elice.websocket.entity.Board;
import com.elice.websocket.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;

    @PostMapping("/post/{userId}")
    public ResponseEntity<Board> createBoard(@RequestBody BoardDTO boardDTO, @PathVariable Long userId) {
        Board board = boardService.createBoard(boardDTO, userId);
        return ResponseEntity.ok(board);
    }

    @PostMapping("/update/{boardId}/{userId}")
    public ResponseEntity<Board> updateBoard(
            @PathVariable Long boardId,
            @PathVariable Long userId,
            @RequestBody BoardDTO boardDTO) {
        Board updatedBoard = boardService.updateBoard(boardId, userId, boardDTO.getTitle(), boardDTO.getContent());
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/delete/{boardId}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long boardId) {
        String deleteBaordId = boardService.deleteBoard(boardId);
        return ResponseEntity.ok(deleteBaordId);
    }
    @GetMapping
    public ResponseEntity<List<Board>> getBoards() {
        return ResponseEntity.ok(boardService.getAllBoard());
    }
}
