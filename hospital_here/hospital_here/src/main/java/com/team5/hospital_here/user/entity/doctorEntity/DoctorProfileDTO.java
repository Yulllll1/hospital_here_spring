package com.team5.hospital_here.user.entity.doctorEntity;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorProfileDTO {
    @NotEmpty(message = "유저 정보가 없습니다.")
    private Long userId;
    @NotEmpty(message = "병원 정보가 없습니다.")
    private Long HospitalId;
    @NotEmpty(message = "진료과 정보가 없습니다.")
    private String major;
}
