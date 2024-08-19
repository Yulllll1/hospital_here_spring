package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.hospital.Mapper.ReservationMapper;
import com.team5.hospital_here.hospital.dto.ReservationRequestDto;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.entity.Reservation;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import com.team5.hospital_here.hospital.repository.ReservationRepository;
import com.team5.hospital_here.review.entity.ReviewMapper;
import com.team5.hospital_here.user.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    public boolean isConflict(LocalDate date, LocalTime timeSlot) {
        return reservationRepository.existsByDateAndTimeSlot(date, timeSlot);
    }

    public void createAndSaveReservation(LocalDate date, LocalTime timeSlot, User user, Long hospitalId) {
        Hospital hospital = hospitalRepository.findById(hospitalId).get();
        Reservation reservation = Reservation.builder()
            .date(date)
            .timeSlot(timeSlot)
            .user(user)
            .hospital(hospital)
            .build();

        reservationRepository.save(reservation);
    }

    public List<ReservationRequestDto> usersReservations(CustomUser customUser)
    {
        List<Reservation> reservations = reservationRepository.findByUserId(customUser.getUser().getId());
        return reservations.stream()
                .map(ReservationMapper::toReservationRequestDto)
                .toList();
    }

    public void deleteReservation(CustomUser customUser, Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();

        if(Objects.equals(customUser.getUser().getId(), reservation.getUser().getId()))
        {
            reservationRepository.delete(reservation);
        }
        else
        {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }
    }
}
