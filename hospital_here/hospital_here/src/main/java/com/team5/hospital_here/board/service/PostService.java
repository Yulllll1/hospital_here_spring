package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.user.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto, CustomUser customUser);
    PostResponseDto updatePost(PostUpdateDto postUpdateDto, Long userId);
    void deletePost(Long id, Long userId);
    Page<PostResponseDto> findAllPosts(Pageable pageable);
    List<PostResponseDto> findPostsByUser(User user);
    Page<PostResponseDto> findPostsByBoardId(Long boardId, Pageable pageable);
    Optional<PostResponseDto> findPostById(Long id);
    Page<PostResponseDto> searchPostsByTitle(String title, Pageable pageable);
    Page<PostResponseDto> findPostsByUserName(String userName, Pageable pageable);

}
