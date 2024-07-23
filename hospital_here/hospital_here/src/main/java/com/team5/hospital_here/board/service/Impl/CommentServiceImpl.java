package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;
import com.team5.hospital_here.board.repository.CommentRepository;
import com.team5.hospital_here.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Override
    public List<CommentResponseDto> findAll() {
        return null;
    }

    @Override
    public Optional<CommentResponseDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public CommentResponseDto save(CommentRequestDto commentRequestDto) {
        return null;
    }

    @Override
    public CommentResponseDto update(CommentUpdateDto commentUpdateDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
