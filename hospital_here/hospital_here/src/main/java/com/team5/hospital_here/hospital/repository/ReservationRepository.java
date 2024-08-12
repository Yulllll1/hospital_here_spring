package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalTime;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    boolean existsByDepartmentAndDateAndTimeSlot(String department, LocalDate date, LocalTime timeSlot);
}
