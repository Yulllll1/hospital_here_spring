package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.dto.board.BoardRequestDto;
import com.team5.hospital_here.board.dto.board.BoardResponseDto;
import com.team5.hospital_here.board.dto.board.BoardUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardService {
    BoardResponseDto createBoard(BoardRequestDto boardRequestDto);
    BoardResponseDto updateBoard(BoardUpdateDto boardUpdateDto);
    void deleteBoard(Long id);
    Page<BoardResponseDto> findAllBoards(Pageable pageable);
    Optional<BoardResponseDto> findBoardById(Long id);

}