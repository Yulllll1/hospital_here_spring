package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;
import com.team5.hospital_here.common.jwt.CustomUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentRequestDto, CustomUser customUser);
    CommentResponseDto updateComment(Long id, CommentUpdateDto commentUpdateDto, CustomUser customUser);
    void deleteComment(Long id, CustomUser customUser);
    List<CommentResponseDto> findAllComments();
    Optional<CommentResponseDto> findCommentById(Long id);
    //List<CommentResponseDto> findCommentsByPostId(Long postId);
    Page<CommentResponseDto> findCommentsByPostId(Long postId, Pageable pageable);

}

