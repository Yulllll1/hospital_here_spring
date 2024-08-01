package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);
    PostResponseDto updatePost(PostUpdateDto postUpdateDto);
    void deletePost(Long id);
    Page<PostResponseDto> findAllPosts(Pageable pageable);
    Page<PostResponseDto> findPostsByBoardId(Long boardId, Pageable pageable);
    Optional<PostResponseDto> findPostById(Long id);
    Page<PostResponseDto> searchPostsByTitle(String title, Pageable pageable);

}
