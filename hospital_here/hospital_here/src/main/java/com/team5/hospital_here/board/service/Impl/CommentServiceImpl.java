package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.User;
import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;
import com.team5.hospital_here.board.repository.CommentRepository;
import com.team5.hospital_here.board.repository.PostRepository;
import com.team5.hospital_here.board.repository.UserRepository;
import com.team5.hospital_here.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Override
    public List<CommentResponseDto> findByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentResponseDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            dtos.add(comment.toResponseDto());
        }
        return dtos;
    }

    @Override
    public CommentResponseDto save(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post id를 찾을 수 없습니다."));

        User user = userRepository.findById(commentRequestDto.getUserId())
                .orElseThrow(() ->new IllegalArgumentException("user id를 찾을 수 없습니다."));

        Comment parent = commentRequestDto.getParentId() != null ?
                commentRepository.findById(commentRequestDto.getParentId())
                .orElseThrow(() -> new IllegalArgumentException("comment id를 찾을 수 없습니다.")) : null;

        Comment comment = commentRequestDto.toEntity(post, user, parent);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.toResponseDto();
    }

    @Override
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public CommentResponseDto update(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("comment id를 찾을 수 없습니다."));

        Post post = postRepository.findById(commentUpdateDto.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("post id를 찾을 수 없습니다."));

        User user = userRepository.findById(commentUpdateDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user id를 찾을 수 없습니다."));

        Comment parent = commentUpdateDto.getParentId() != null ?
                commentRepository.findById(commentUpdateDto.getParentId())
                        .orElseThrow(() -> new IllegalArgumentException("comment id를 찾을 수 없습니다.")) : null;

        comment.update(commentUpdateDto, post, user, parent);
        Comment updatedComment = commentRepository.save(comment);
        return updatedComment.toResponseDto();
    }
}
