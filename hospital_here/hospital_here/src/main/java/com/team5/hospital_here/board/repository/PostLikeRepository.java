package com.team5.hospital_here.board.repository;

import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.PostLike;
import com.team5.hospital_here.user.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByUserAndPost(User user, Post post);
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
