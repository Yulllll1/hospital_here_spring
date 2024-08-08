package com.team5.hospital_here.review.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.user.entity.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
public class ReviewDTO {

    private Long id;
    private Long userId;
    private Long hospitalId;
    private int rating;
    private String content;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDateTime created_At;


}
