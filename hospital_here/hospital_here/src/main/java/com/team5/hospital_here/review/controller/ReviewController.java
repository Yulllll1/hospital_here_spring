package com.team5.hospital_here.review.controller;


import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.review.entity.ReviewDTO;
import com.team5.hospital_here.review.repository.ReviewRepository;
import com.team5.hospital_here.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewRepository reviewRepository;

    public ReviewController(ReviewService reviewService, ReviewRepository reviewRepository) {
        this.reviewService = reviewService;
        this.reviewRepository = reviewRepository;
    }


    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReviewDTO>> getReview(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.findByUser(userId));
    }

    @PostMapping("/{hospitalId}")
    public ResponseEntity<String> addReview(@PathVariable Long hospitalId, @RequestBody ReviewDTO reviewDTO) {
        reviewService.createReview(hospitalId,reviewDTO);
        return ResponseEntity.ok("리뷰 생성 성공");
    }

    @PutMapping("/{reviewId}")
    public ResponseEntity<String> updateReview(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reviewId, @RequestBody ReviewDTO reviewDTO) {
        reviewService.updateReview(customUser,reviewId,reviewDTO);
        return ResponseEntity.ok("리뷰 수정 성공");
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<String> deleteReview(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reviewId) {
        reviewService.deleteReview(customUser, reviewId);
        return ResponseEntity.ok("리뷰 삭제 성공");
    }

    @GetMapping("/{hospitalId}")
    public ResponseEntity<List<ReviewDTO>> getReviewByHospital(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(reviewService.findByHospitalId(hospitalId));
    }
}
