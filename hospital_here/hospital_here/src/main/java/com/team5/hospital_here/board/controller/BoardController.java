package com.team5.hospital_here.board.controller;

import com.team5.hospital_here.board.dto.board.BoardRequestDto;
import com.team5.hospital_here.board.dto.board.BoardResponseDto;
import com.team5.hospital_here.board.dto.board.BoardUpdateDto;
import com.team5.hospital_here.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
        List<BoardResponseDto> boards = boardService.findAll();
        return ResponseEntity.ok(boards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BoardResponseDto> getBoardById(@PathVariable Long id) {
        Optional<BoardResponseDto> board = boardService.findById(id);
        return board.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<BoardResponseDto> createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto createdBoard = boardService.save(boardRequestDto);
        return ResponseEntity.ok(createdBoard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BoardResponseDto> updateBoard(@PathVariable Long id, @RequestBody BoardUpdateDto boardUpdateDto) {
        boardUpdateDto.setId(id); // ensure the ID is set
        BoardResponseDto updatedBoard = boardService.update(boardUpdateDto);
        return ResponseEntity.ok(updatedBoard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
