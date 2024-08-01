package com.team5.hospital_here.hospital.repository;

import com.team5.hospital_here.hospital.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByDateAndTimeSlot(LocalDate date, LocalTime timeSlot);
}

