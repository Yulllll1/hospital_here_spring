package com.team5.hospital_here.review.entity;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class AvgReviewDTO {

    private double avgRating;
    private int reviewCount;
}
