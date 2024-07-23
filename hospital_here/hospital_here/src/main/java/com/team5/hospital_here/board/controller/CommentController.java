package com.team5.hospital_here.board.controller;

import com.team5.hospital_here.board.dto.comment.CommentRequestDto;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;
import com.team5.hospital_here.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<CommentResponseDto>> getCommentsByPostId(@PathVariable Long postId) {
        List<CommentResponseDto> comments = commentService.findByPostId(postId);
        return ResponseEntity.ok(comments);
    }

    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.save(commentRequestDto);
        return ResponseEntity.ok(commentResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentUpdateDto commentUpdateDto) {
        CommentResponseDto updatedComment = commentService.update(id, commentUpdateDto);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
