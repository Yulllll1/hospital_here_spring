package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Reservation;
import com.team5.hospital_here.hospital.repository.ReservationRepository;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isConflict(String department, LocalDate date, LocalTime timeSlot) {
        List<Reservation> existingReservations = reservationRepository.findByDateAndTimeSlot(date, timeSlot);
        return existingReservations.stream()
            .anyMatch(reservation -> department.equals(reservation.getDepartment()));
    }

    public Reservation createAndSaveReservation(String department, LocalDate date, LocalTime timeSlot, Long userId) {
        Reservation reservation = new Reservation();
        reservation.setDepartment(department);
        reservation.setDate(date);
        reservation.setTimeSlot(timeSlot);

        Optional<User> user = userRepository.findById(userId);
        user.ifPresent(reservation::setUser);

        return reservationRepository.save(reservation);
    }
}
