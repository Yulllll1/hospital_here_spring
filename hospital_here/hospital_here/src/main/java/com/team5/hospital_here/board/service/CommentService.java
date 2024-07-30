package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentRequestDto);
    CommentResponseDto updateComment(Long id, CommentUpdateDto commentUpdateDto);
    void deleteComment(Long id);
    List<CommentResponseDto> findAllComments();
    Optional<CommentResponseDto> findCommentById(Long id);
    List<CommentResponseDto> findCommentsByPostId(Long postId);

}

