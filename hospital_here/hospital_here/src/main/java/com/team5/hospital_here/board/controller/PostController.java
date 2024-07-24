package com.team5.hospital_here.board.controller;

import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.dto.postImg.PostImgRequestDto;
import com.team5.hospital_here.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto postResponseDto = postService.createPost(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(postResponseDto);
    }

    @PostMapping("/{postId}/images")
    public ResponseEntity<Void> addPostImage(@PathVariable Long postId, @RequestBody PostImgRequestDto postImgRequestDto) {
        postService.addPostImage(postId, postImgRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        postUpdateDto.setId(id);
        PostResponseDto postResponseDto = postService.updatePost(postUpdateDto);
        return ResponseEntity.ok(postResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        postService.deletePost(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.findAllPosts();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        return postService.findPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<PostResponseDto>> getPostsByBoardId(@PathVariable Long boardId) {
        List<PostResponseDto> posts = postService.findPostsByBoardId(boardId);
        return ResponseEntity.ok(posts);
    }
    @GetMapping("/search")
    public ResponseEntity<List<PostResponseDto>> searchPostsByTitle(@RequestParam("title") String title) {
        List<PostResponseDto> posts = postService.searchPostsByTitle(title);
        return ResponseEntity.ok(posts);
    }
}