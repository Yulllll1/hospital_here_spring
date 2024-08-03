package com.team5.hospital_here.review.service;

import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import com.team5.hospital_here.review.entity.ReviewDTO;
import com.team5.hospital_here.review.entity.ReviewEntity;
import com.team5.hospital_here.review.entity.ReviewMapper;
import com.team5.hospital_here.review.repository.ReviewRepository;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final HospitalRepository hospitalRepository;
    private final UserRepository userRepository;

    public ReviewService(ReviewRepository reviewRepository, HospitalRepository hospitalRepository, UserRepository userRepository) {
        this.reviewRepository = reviewRepository;
        this.hospitalRepository = hospitalRepository;
        this.userRepository = userRepository;
    }

    public List<ReviewDTO> findByUser(Long userId) {

        List<ReviewEntity> reviewEntities = reviewRepository.findByUserId(userId);


        return reviewEntities.stream()
                .map(ReviewMapper::toReviewDTO)
                .toList();
    }

    public void createReview(Long hospitalId, ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = ReviewMapper.toReviewEntity(reviewDTO);

        reviewEntity.setHospital(hospitalRepository.findById(hospitalId).get());
        reviewEntity.setUser(userRepository.findById(reviewDTO.getUserId()).get());

        reviewRepository.save(reviewEntity);
    }

    public void deleteReview(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }

    public void updateReview(Long reviewId, ReviewDTO reviewDTO) {

        ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElse(null);

        reviewEntity.setContent(reviewDTO.getContent());
        reviewEntity.setRating(reviewDTO.getRating());

        reviewRepository.save(reviewEntity);
    }


}
