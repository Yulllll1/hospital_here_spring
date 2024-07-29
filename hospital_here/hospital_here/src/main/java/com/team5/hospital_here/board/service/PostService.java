package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.dto.postImg.PostImgRequestDto;
import com.team5.hospital_here.user.entity.user.User;

import java.util.List;
import java.util.Optional;

public interface PostService {
    PostResponseDto createPost(PostRequestDto postRequestDto);
    PostResponseDto updatePost(PostUpdateDto postUpdateDto);
    void deletePost(Long id);
    List<PostResponseDto> findAllPosts();
    Optional<PostResponseDto> findPostById(Long id);
    List<PostResponseDto> findPostsByUser(User user);
    List<PostResponseDto> findPostsByBoardId(Long boardId);
    List<PostResponseDto> searchPostsByTitle(String title);
    void addPostImage(Long postId, PostImgRequestDto postImgRequestDto);
}
