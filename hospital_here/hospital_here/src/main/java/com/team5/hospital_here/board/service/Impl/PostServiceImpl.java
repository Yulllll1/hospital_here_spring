package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.repository.PostRepository;
import com.team5.hospital_here.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Page<PostResponseDto> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostResponseDto> findByBoardId(Long boardId, Pageable pageable) {
        return null;
    }

    @Override
    public Page<PostResponseDto> findByUserId(Long userId, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<PostResponseDto> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public PostResponseDto save(PostRequestDto postRequestDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }
}
