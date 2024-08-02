package com.team5.hospital_here.board.repository;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByPostId(Long postId);
    Page<Comment> findByPostId(Long postId, Pageable pageable);
    //Page<CommentResponseDto> findCommentsByPostId(Long postId, Pageable pageable);
}
