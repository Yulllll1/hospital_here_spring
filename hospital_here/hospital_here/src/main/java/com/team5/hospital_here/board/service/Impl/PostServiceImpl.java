package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.PostImg;
import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.dto.postImg.PostImgResponseDto;
import com.team5.hospital_here.board.repository.BoardRepository;
import com.team5.hospital_here.board.repository.PostImgRepository;
import com.team5.hospital_here.board.repository.PostRepository;
import com.team5.hospital_here.board.service.PostService;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final PostImgRepository postImgRepository;
    @Transactional
    @Override
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Board board = boardRepository.findById(postRequestDto.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));
//        User user = userRepository.findById(postRequestDto.getUserId())
//                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        //임시로 설정------------------------------------------------------------
        User user = userRepository.findById(1L)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        //---------------------------------------------------------------------
        Post post = postRequestDto.toEntity(board, user);
        Post createdPost = postRepository.save(post);

        if (postRequestDto.getImageUrl() != null) {
            PostImg postImg = PostImg.builder()
                    .link(postRequestDto.getImageUrl())
                    .build();

            createdPost.addPostImg(postImg);

            postImgRepository.save(postImg);
            createdPost = postRepository.save(createdPost);
        }
        return createdPost.toResponseDto();
    }
    @Transactional
    @Override
    public PostResponseDto updatePost(PostUpdateDto postUpdateDto) {
        Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        post.update(postUpdateDto);
        Post updatedPost = postRepository.save(post);
        return updatedPost.toResponseDto();
    }
    @Transactional
    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Page<PostResponseDto> findAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        return posts.map(Post::toResponseDto);
    }

    @Override
    public List<PostResponseDto> findPostsByUser(User user) {
        return postRepository.findByUser(user).stream().map(Post::toResponseDto).toList();
    }


    @Override
    public Page<PostResponseDto> findPostsByBoardId(Long boardId, Pageable pageable) {
        Page<Post> posts = postRepository.findByBoardId(boardId, pageable);
        return posts.map(Post::toResponseDto);
    }

    @Override
    public Optional<PostResponseDto> findPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            return Optional.ofNullable(post.toResponseDto());
        }
        return Optional.empty();
    }

    @Override
    public Page<PostResponseDto> searchPostsByTitle(String title, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContainingIgnoreCase(title, pageable);
        return posts.map(Post::toResponseDto);
    }


}

