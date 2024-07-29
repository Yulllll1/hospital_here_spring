package com.team5.hospital_here.hospital.dto;


import java.time.LocalDate;
import java.time.LocalTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequestDto {
    private String department;
    private LocalDate date;
    private LocalTime timeSlot;

}
