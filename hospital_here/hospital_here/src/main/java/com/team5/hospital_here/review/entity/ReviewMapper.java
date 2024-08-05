package com.team5.hospital_here.review.entity;

import com.team5.hospital_here.common.baseEntity.BaseEntity;

public class ReviewMapper {


    public static ReviewEntity toReviewEntity(ReviewDTO reviewDTO)
    {
        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setId(reviewDTO.getId());
        reviewEntity.setContent(reviewDTO.getContent());
        reviewEntity.setRating(reviewDTO.getRating());
        return reviewEntity;
    }
    public static ReviewDTO toReviewDTO(ReviewEntity reviewEntity)
    {
        ReviewDTO reviewDTO = new ReviewDTO();
        reviewDTO.setId(reviewEntity.getId());
        reviewDTO.setUserId(reviewEntity.getUser().getId());
        reviewDTO.setContent(reviewEntity.getContent());
        reviewDTO.setRating(reviewEntity.getRating());
        reviewDTO.setHospitalId(reviewEntity.getHospital().getId());
        return reviewDTO;
    }
}
