package com.team5.hospital_here.review.service;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import com.team5.hospital_here.review.entity.AvgReviewDTO;
import com.team5.hospital_here.review.entity.ReviewDTO;
import com.team5.hospital_here.review.entity.ReviewEntity;
import com.team5.hospital_here.review.entity.ReviewMapper;
import com.team5.hospital_here.review.repository.ReviewRepository;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
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
    public String findUserNameById(Long id){
        return userRepository.findById(id).get().getName();
    }

    public void createReview(Long hospitalId, ReviewDTO reviewDTO) {
        ReviewEntity reviewEntity = ReviewMapper.toReviewEntity(reviewDTO);

        reviewEntity.setHospital(hospitalRepository.findById(hospitalId).get());
        reviewEntity.setUser(userRepository.findById(reviewDTO.getUserId()).get());

        reviewRepository.save(reviewEntity);
    }

    public void deleteReview(CustomUser customUser, Long reviewId) {

        Optional<ReviewEntity> review = reviewRepository.findById(reviewId);
        Long userId = review.get().getUser().getId();
        log.info("접속중 유저 아이디 : {}",customUser.getUser().getId());
        log.info("삭제할 리뷰 유저 아이디: {}",userId);
        if(userId.equals(customUser.getUser().getId()))
        {
            reviewRepository.deleteById(reviewId);
        }
        else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

    }

    public void updateReview(CustomUser customUser, Long reviewId, ReviewDTO reviewDTO) {

        log.info("유저 아이디 :{}",customUser.getUser().getId());
        log.info("리뷰적은 유저 아이디:{}",reviewRepository.findById(reviewId).get().getUser().getId());
        if(customUser.getUser().getId().equals(reviewRepository.findById(reviewId).get().getUser().getId())){
            ReviewEntity reviewEntity = reviewRepository.findById(reviewId).orElse(null);

            reviewEntity.setContent(reviewDTO.getContent());
            reviewEntity.setRating(reviewDTO.getRating());

            reviewRepository.save(reviewEntity);
        }
        else {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

    }

    public List<ReviewDTO> findByHospitalId(Long hospitalId)
    {
        List<ReviewEntity> reviewEntities = reviewRepository.findByHospitalId(hospitalId);


        return reviewEntities.stream()
                .map(ReviewMapper::toReviewDTO)
                .toList();
    }

    public Page<ReviewDTO> findByUserPage(Long userId, Pageable pageable) {
        Page<ReviewEntity> reviewEntities = reviewRepository.findByUserId(userId, pageable);
        return reviewEntities.map(ReviewMapper::toReviewDTO);
    }

    public Page<ReviewDTO> findByHospitalIdPage(Long hospitalId, Pageable pageable) {
        Page<ReviewEntity> reviewEntities = reviewRepository.findByHospitalId(hospitalId, pageable);
        return reviewEntities.map(ReviewMapper::toReviewDTO);
    }
    public AvgReviewDTO avgRating(Long hospitalId) {

        Optional<Double> avgOptional = reviewRepository.findAverageRating(hospitalId);
        double avg = avgOptional.orElse(0.0);
        int count = reviewRepository.findByHospitalId(hospitalId).size();
        double roundedAvg = Math.round(avg * 10) / 10.0;
        AvgReviewDTO avgReviewDTO = new AvgReviewDTO();
        avgReviewDTO.setAvgRating(roundedAvg);
        avgReviewDTO.setReviewCount(count);
        return avgReviewDTO;

    }


}
