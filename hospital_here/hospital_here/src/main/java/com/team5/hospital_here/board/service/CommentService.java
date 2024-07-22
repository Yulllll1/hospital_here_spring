package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    List<CommentResponseDto> findAll();
    Optional<CommentResponseDto> findById(Long id);
    CommentResponseDto save(CommentRequestDto commentRequestDto);
    void deleteById(Long id);
}
