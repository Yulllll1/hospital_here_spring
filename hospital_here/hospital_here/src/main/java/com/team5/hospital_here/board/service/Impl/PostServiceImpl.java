package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.User;
import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.repository.BoardRepository;
import com.team5.hospital_here.board.repository.PostRepository;
import com.team5.hospital_here.board.repository.UserRepository;
import com.team5.hospital_here.board.service.PostService;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    @Override
    public List<PostResponseDto> findAll() {
        List<Post> posts = postRepository.findAll();
        List<PostResponseDto> dtos = new ArrayList<>();
        for (Post post : posts) {
            dtos.add(post.toResponseDto());
        }
        return dtos;
    }

    @Override
    public Optional<PostResponseDto> findById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return Optional.of(post.toResponseDto());
        }
        return Optional.empty();
    }

    @Override
    public PostResponseDto save(PostRequestDto postRequestDto) {
        Board board = boardRepository.findById(postRequestDto.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = postRequestDto.toEntity(board, user);
        Post savedPost = postRepository.save(post);
        return savedPost.toResponseDto();
    }

    @Override
    public PostResponseDto update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
        Board board = boardRepository.findById(postUpdateDto.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
        User user = userRepository.findById(postUpdateDto.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        post.update(postUpdateDto, board, user);
        Post updatedPost = postRepository.save(post);
        return updatedPost.toResponseDto();
    }

    @Override
    public Page<PostResponseDto> findByBoardId(Long boardId, Pageable pageable) {
        return postRepository.findByBoardId(boardId, pageable).map(Post::toResponseDto);
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

}

