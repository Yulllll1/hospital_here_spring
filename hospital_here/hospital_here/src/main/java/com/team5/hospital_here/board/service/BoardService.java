package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.dto.board.BoardRequestDto;
import com.team5.hospital_here.board.dto.board.BoardResponseDto;
import com.team5.hospital_here.board.dto.board.BoardUpdateDto;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    BoardResponseDto createBoard(BoardRequestDto boardRequestDto);
    BoardResponseDto updateBoard(BoardUpdateDto boardUpdateDto);
    void deleteBoard(Long id);
    List<BoardResponseDto> findAllBoards();
    Optional<BoardResponseDto> findBoardById(Long id);

}