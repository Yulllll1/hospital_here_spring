package com.team5.hospital_here.review.controller;


import com.team5.hospital_here.review.entity.ReviewDTO;
import com.team5.hospital_here.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReview(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.findByUser(userId));
    }

    @PostMapping("/{hospitalId}")
    public ResponseEntity<String> addReview(@PathVariable Long hospitalId, @RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(hospitalId,reviewDTO);
        return ResponseEntity.ok("리뷰 생성 성공");
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReview(reviewId,reviewDTO);
        return ResponseEntity.ok("리뷰 수정 성공");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.ok("리뷰 삭제 성공");
    }
}
