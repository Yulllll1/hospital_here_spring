package com.team5.hospital_here.user.entity.user.doctorEntity;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DoctorProfileDTO {
    @NotNull(message = "유저 정보가 없습니다.")
    private Long userId;
    @NotNull(message = "병원 정보가 없습니다.")
    private Long hospitalId;
    @NotBlank(message = "진료과 정보가 없습니다.")
    private String major;
}
