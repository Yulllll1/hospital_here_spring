package com.team5.hospital_here.board.service;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {
    List<PostResponseDto> findAll();
    Optional<PostResponseDto> findById(Long id);
    PostResponseDto save(PostRequestDto postRequestDto);
    PostResponseDto update(PostUpdateDto postUpdateDto);
    void deleteById(Long id);

    //Page<PostResponseDto> findAllByBoardId(Long boardId, Pageable pageable);
}
