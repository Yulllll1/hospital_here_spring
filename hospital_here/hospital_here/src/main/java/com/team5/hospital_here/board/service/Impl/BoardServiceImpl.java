package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.dto.board.BoardRequestDto;
import com.team5.hospital_here.board.dto.board.BoardResponseDto;
import com.team5.hospital_here.board.dto.board.BoardUpdateDto;
import com.team5.hospital_here.board.repository.BoardRepository;
import com.team5.hospital_here.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public List<BoardResponseDto> findAll() {
        List<Board> boards = boardRepository.findAll();
        List<BoardResponseDto> dtos = new ArrayList<>();
        for (Board board : boards) {
            dtos.add(board.toResponseDto());
        }
        return dtos;
    }

    @Override
    public Optional<BoardResponseDto> findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            return Optional.of(board.toResponseDto());
        }
        return Optional.empty();
    }
    @Override
    public BoardResponseDto save(BoardRequestDto boardRequestDto) {
        Board board = boardRequestDto.toEntity();
        Board saveBoard = boardRepository.save(board);
        return saveBoard.toResponseDto();
    }

    @Override
    public BoardResponseDto update(BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        board.update(boardUpdateDto);
        Board updatedBoard = boardRepository.save(board);
        return updatedBoard.toResponseDto();
    }

    @Override
    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }
}

