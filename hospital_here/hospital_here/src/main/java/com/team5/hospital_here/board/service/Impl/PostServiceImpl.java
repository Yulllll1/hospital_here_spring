package com.team5.hospital_here.board.service.Impl;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.PostImg;
import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.repository.BoardRepository;
import com.team5.hospital_here.board.repository.PostImgRepository;
import com.team5.hospital_here.board.repository.PostRepository;
import com.team5.hospital_here.board.service.PostService;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.CustomUser;
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
    public PostResponseDto createPost(PostRequestDto postRequestDto, CustomUser customUser) {
        Board board = boardRepository.findById(postRequestDto.getBoardId())
                .orElseThrow(() -> new CustomException(ErrorCode.BOARD_NOT_FOUND));

        User user = userRepository.findById(customUser.getUser().getId())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Post post = postRequestDto.toEntity(board, user);
        Post createdPost = postRepository.save(post);

        savePostImages(postRequestDto.getImageUrls(), createdPost);

        return createPostResponseDto(createdPost, user);
    }

    @Transactional
    @Override
    public PostResponseDto updatePost(PostUpdateDto postUpdateDto, Long userId) {
        Post post = postRepository.findById(postUpdateDto.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_UPDATE_DENIED);
        }

        post.update(postUpdateDto);

        updatePostImages(postUpdateDto.getImageUrls(), post);

        Post updatedPost = postRepository.save(post);
        return updatedPost.toResponseDto();
    }

    @Transactional
    @Override
    public void deletePost(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        if (!post.getUser().getId().equals(userId)) {
            throw new CustomException(ErrorCode.POST_DELETE_DENIED);
        }

        postRepository.delete(post);
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
        return optionalPost.map(Post::toResponseDto);
    }

    @Override
    public Page<PostResponseDto> searchPostsByTitle(String title, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContainingIgnoreCase(title, pageable);
        return posts.map(Post::toResponseDto);
    }

    @Override
    public Page<PostResponseDto> findPostsByUserName(String userName, Pageable pageable) {
        User user = userRepository.findByName(userName)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Page<Post> posts = postRepository.findByUser(user, pageable);
        return posts.map(Post::toResponseDto);
    }


    private void savePostImages(List<String> imageUrls, Post post) {
        if (imageUrls != null && !imageUrls.isEmpty()) {
            for (String imageUrl : imageUrls) {
                PostImg postImg = PostImg.builder()
                        .link(imageUrl)
                        .post(post)
                        .build();
                post.addPostImg(postImg);
                postImgRepository.save(postImg);
            }
        }
    }

    private void updatePostImages(List<String> newImageUrls, Post post) {
        List<PostImg> existingImages = post.getPostImgs();
        List<String> existingImageUrls = existingImages.stream()
                .map(PostImg::getLink)
                .toList();

        for (String imageUrl : newImageUrls) {
            if (!existingImageUrls.contains(imageUrl)) {
                PostImg postImg = PostImg.builder()
                        .link(imageUrl)
                        .post(post)
                        .build();
                post.addPostImg(postImg);
                postImgRepository.save(postImg);
            }
        }

        for (PostImg postImg : existingImages) {
            if (!newImageUrls.contains(postImg.getLink())) {
                postImgRepository.delete(postImg);
            }
        }
    }

    private PostResponseDto createPostResponseDto(Post post, User user) {
        return PostResponseDto.builder()
                .id(post.getId())
                .boardId(post.getBoard().getId())
                .userId(user.getId())
                .userName(user.getName())
                .title(post.getTitle())
                .content(post.getContent())
                .imageUrls(post.getPostImgs().stream()
                        .map(PostImg::toResponseDto)
                        .toList())
                .createdAt(post.getCreatedAt())
                .build();
    }
}