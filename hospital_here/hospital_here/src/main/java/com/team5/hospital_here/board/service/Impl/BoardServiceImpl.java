package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.dto.board.BoardRequestDto;
import com.team5.hospital_here.board.dto.board.BoardResponseDto;
import com.team5.hospital_here.board.dto.board.BoardUpdateDto;
import com.team5.hospital_here.board.repository.BoardRepository;
import com.team5.hospital_here.board.service.BoardService;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    @Override
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board board = boardRequestDto.toEntity();
        Board createdBoard = boardRepository.save(board);
        return createdBoard.toResponseDto();
    }

    @Override
    public BoardResponseDto updateBoard(BoardUpdateDto boardUpdateDto) {
        Board board = boardRepository.findById(boardUpdateDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        board.update(boardUpdateDto);
        Board updatedBoard = boardRepository.save(board);
        return updatedBoard.toResponseDto();
    }

    @Override
    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }

    @Override
    public List<BoardResponseDto> findAllBoards() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(Board::toResponseDto).toList();
    }

    @Override
    public Optional<BoardResponseDto> findBoardById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            return Optional.ofNullable(board.toResponseDto());
        }
        return Optional.empty();
    }
}
