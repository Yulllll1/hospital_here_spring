package com.team5.hospital_here.hospital.dto;


import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequestDto {
    private Long id;
    private LocalDate date;
    private LocalTime timeSlot;
    private Long userId;
    private String userName;
    private Long hospitalid;

}
