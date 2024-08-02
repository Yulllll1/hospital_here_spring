package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Reservation;
import com.team5.hospital_here.hospital.repository.ReservationRepository;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Reservation> getReservations(LocalDate date, LocalTime timeSlot) {
        return reservationRepository.findByDateAndTimeSlot(date, timeSlot);
    }

    public Reservation createReservation(String department, LocalDate date, LocalTime timeSlot, Long userId) {
        Reservation reservation = new Reservation();
        reservation.setDepartment(department);
        reservation.setDate(date);
        reservation.setTimeSlot(timeSlot);

        // 사용자 엔티티를 가져와서 설정
        User user = userRepository.findById(userId).orElse(null);
        reservation.setUser(user);

        return reservation;
    }

    public Reservation save(Reservation reservation) {
        return reservationRepository.save(reservation);
    }
}
