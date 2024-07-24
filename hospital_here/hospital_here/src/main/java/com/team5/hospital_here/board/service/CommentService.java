package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<CommentResponseDto> findById(Long id);
    List<CommentResponseDto> findByPostId(Long postId);
    CommentResponseDto save(CommentRequestDto commentRequestDto);
    void deleteById(Long id);
    CommentResponseDto update(Long id, CommentUpdateDto commentUpdateDto);
}

