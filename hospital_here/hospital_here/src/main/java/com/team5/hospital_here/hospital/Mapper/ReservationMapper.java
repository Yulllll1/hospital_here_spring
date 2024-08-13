package com.team5.hospital_here.hospital.Mapper;

import com.team5.hospital_here.hospital.dto.ReservationRequestDto;
import com.team5.hospital_here.hospital.entity.Reservation;
import org.springframework.stereotype.Component;


public class ReservationMapper {

    public static ReservationRequestDto toReservationRequestDto(Reservation reservation) {
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto();
        reservationRequestDto.setId(reservation.getId());
        reservationRequestDto.setDate(reservation.getDate());
        reservationRequestDto.setTimeSlot(reservation.getTimeSlot());
        reservationRequestDto.setUserId(reservation.getUser().getId());
        reservationRequestDto.setUserName(reservation.getUser().getName());
        reservationRequestDto.setHospitalid(reservation.getHospital().getId());
        return reservationRequestDto;
    }

}
