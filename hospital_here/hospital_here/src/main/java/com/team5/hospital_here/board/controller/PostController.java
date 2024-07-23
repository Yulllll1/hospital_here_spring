package com.team5.hospital_here.board.controller;

import com.team5.hospital_here.board.dto.post.PostRequestDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.board.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        List<PostResponseDto> posts = postService.findAll();
        return ResponseEntity.ok(posts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long id) {
        Optional<PostResponseDto> postOptional = postService.findById(id);
        if (postOptional.isPresent()) {
            return ResponseEntity.ok(postOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto) {
        PostResponseDto savedPost = postService.save(postRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody PostUpdateDto postUpdateDto) {
        postUpdateDto.setId(id);
        PostResponseDto updatedPost = postService.update(postUpdateDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Optional<PostResponseDto> postOptional = postService.findById(id);
        if (postOptional.isPresent()) {
            postService.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

//    @GetMapping("/board/{boardId}")
//    public ResponseEntity<Page<PostResponseDto>> getPostsByBoardId(@PathVariable Long boardId, Pageable pageable) {
//        Page<PostResponseDto> postPage = postService.findAllByBoardId(boardId, pageable);
//        return ResponseEntity.ok(postPage);
//    }
}



