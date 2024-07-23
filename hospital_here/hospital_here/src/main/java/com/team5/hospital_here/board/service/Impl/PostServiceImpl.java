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
                .orElseThrow(() -> new IllegalArgumentException("board id가 존재하지 않습니다."));
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("user id가 존재하지 않습니다."));

        Post post = postRequestDto.toEntity(board, user);
        Post savedPost = postRepository.save(post);
        return savedPost.toResponseDto();
    }

    @Override
    public PostResponseDto update(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        Board board = boardRepository.findById(postUpdateDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        User user = userRepository.findById(postUpdateDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        post.update(postUpdateDto, board, user);
        Post updatedPost = postRepository.save(post);
        return updatedPost.toResponseDto();
    }


    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

//    @Override
//    public Page<PostResponseDto> findAllByBoardId(Long boardId, Pageable pageable) {
//        Page<Post> postPage = postRepository.findAllByBoardId(boardId, pageable);
//        return postPage.map(Post::toResponseDto);
//    }
}

