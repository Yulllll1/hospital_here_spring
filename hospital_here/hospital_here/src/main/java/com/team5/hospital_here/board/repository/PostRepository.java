package com.team5.hospital_here.board.repository;

import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.user.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findAll(Pageable pageable);
    Page<Post> findByBoardId(Long boardId, Pageable pageable);
    Page<Post> findByTitleContainingIgnoreCase(String title, Pageable pageable);
    Page<Post> findByBoardIdOrderByViewCountDesc(Long boardId, Pageable pageable);
    Page<Post> findByBoardIdOrderByLikeCountDesc(Long boardId, Pageable pageable);
}
