package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.dto.board.BoardRequestDto;
import com.team5.hospital_here.board.dto.board.BoardResponseDto;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    List<BoardResponseDto> findAll();
    Optional<BoardResponseDto> findById(Long id);
    BoardResponseDto save(BoardRequestDto boardRequestDto);
    void deleteById(Long id);
}
