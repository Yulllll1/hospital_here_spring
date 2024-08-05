package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;
import com.team5.hospital_here.board.repository.CommentRepository;
import com.team5.hospital_here.board.repository.PostRepository;
import com.team5.hospital_here.board.service.CommentService;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(commentRequestDto.getPostId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
//        User user = userRepository.findById(commentRequestDto.getUserId())
//                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        //임시로 설정------------------------------------------------------------
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        //---------------------------------------------------------------------
        Comment parent = null;
        if (commentRequestDto.getParentId() != null) {
            parent = commentRepository.findById(commentRequestDto.getParentId())
                    .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));
        }

        Comment comment = commentRequestDto.toEntity(post, user, parent);
        Comment savedComment = commentRepository.save(comment);
        return savedComment.toResponseDto();
    }
    @Transactional
    @Override
    public CommentResponseDto updateComment(Long id, CommentUpdateDto commentUpdateDto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.COMMENT_NOT_FOUND));

        comment.update(commentUpdateDto);
        Comment updatedComment = commentRepository.save(comment);
        return updatedComment.toResponseDto();
    }
    @Transactional
    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    public List<CommentResponseDto> findAllComments() {
        List<Comment> comments= commentRepository.findAll();
        return comments.stream().map(Comment::toResponseDto).toList();
    }

    @Override
    public Optional<CommentResponseDto> findCommentById(Long id) {
        Optional<Comment> optionalComment = commentRepository.findById(id);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            return Optional.ofNullable(comment.toResponseDto());
        }
        return Optional.empty();
    }

    @Override
    public Page<CommentResponseDto> findCommentsByPostId(Long postId, Pageable pageable) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").ascending());
        Page<Comment> comments = commentRepository.findByPostId(postId, sortedPageable);
        return comments.map(Comment::toResponseDto);
    }

//    @Override
//    public List<CommentResponseDto> findCommentsByPostId(Long postId) {
//        List<Comment> comments = commentRepository.findByPostId(postId);
//        return comments.stream()
//                .map(Comment::toResponseDto)
//                .collect(Collectors.toList());
//    }
}
