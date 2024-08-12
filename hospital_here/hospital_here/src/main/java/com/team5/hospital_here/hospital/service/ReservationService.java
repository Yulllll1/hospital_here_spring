package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.dto.ReservationRequestDto;
import com.team5.hospital_here.hospital.entity.Reservation;
import com.team5.hospital_here.hospital.repository.ReservationRepository;
import com.team5.hospital_here.user.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    public boolean isConflict(String department, LocalDate date, LocalTime timeSlot) {
        return reservationRepository.existsByDepartmentAndDateAndTimeSlot(department, date, timeSlot);
    }

    public void createAndSaveReservation(String department, LocalDate date, LocalTime timeSlot, User user) {
        Reservation reservation = Reservation.builder()
            .department(department)
            .date(date)
            .timeSlot(timeSlot)
            .user(user)
            .build();

        reservationRepository.save(reservation);
    }
}
