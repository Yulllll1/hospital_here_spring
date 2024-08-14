package com.team5.hospital_here.board.controller;

import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.service.PostService;
import com.team5.hospital_here.common.jwt.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto,
                                                      @AuthenticationPrincipal CustomUser customUser) {

        PostResponseDto postResponseDto = postService.createPost(postRequestDto, customUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id,
                                                      @RequestBody PostUpdateDto postUpdateDto,
                                                      @AuthenticationPrincipal CustomUser customUser) {
        postUpdateDto.setId(id);
        PostResponseDto postResponseDto = postService.updatePost(postUpdateDto, customUser.getUser().getId());
        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id,
                                           @AuthenticationPrincipal CustomUser customUser) {
        postService.deletePost(id, customUser.getUser().getId());
        return ResponseEntity.noContent().build();
    }

    //notUse
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.findAllPosts(pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        return postService.findPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUser customUser) {
        postService.likePost(id, customUser.getUser().getId());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/like")
    public ResponseEntity<Void> unlikePost(
            @PathVariable Long id,
            @AuthenticationPrincipal CustomUser customUser) {
        postService.unlikePost(id, customUser.getUser().getId());
        return ResponseEntity.ok().build();
    }
    @PostMapping("/{id}/view")
    public ResponseEntity<Void> incrementViewCount(@PathVariable Long id) {
        postService.incrementViewCount(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<Page<PostResponseDto>> getPostsByBoardId(@PathVariable Long boardId,
                                                                   @RequestParam(defaultValue = "0") int page,
                                                                   @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.findPostsByBoardId(boardId, pageable);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/search")
    public ResponseEntity<Page<PostResponseDto>> searchPostsByTitle(
            @RequestParam("title") String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.searchPostsByTitle(title, pageable);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/search-my-post")
    public ResponseEntity<Page<PostResponseDto>> searchPostsByUserName(@AuthenticationPrincipal CustomUser customUser,
                                                                       @RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.findPostsByUserName(customUser.getUser().getName(), pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/board/{boardId}/sortedByViewCount")
    public ResponseEntity<Page<PostResponseDto>> getPostsByBoardIdSortedByViewCount(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.findPostsByBoardIdSortedByViewCount(boardId, pageable);
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/board/{boardId}/sortedByLikeCount")
    public ResponseEntity<Page<PostResponseDto>> getPostsByBoardIdSortedByLikeCount(
            @PathVariable Long boardId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "6") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseDto> posts = postService.findPostsByBoardIdSortedByLikeCount(boardId, pageable);
        return ResponseEntity.ok(posts);
    }

}