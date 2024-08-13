package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.hospital.dto.ReservationRequestDto;
import com.team5.hospital_here.hospital.entity.Reservation;
import com.team5.hospital_here.hospital.service.ReservationService;
import com.team5.hospital_here.user.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    // 예약 가능 여부 확인
    @GetMapping("/check-availability")
    public ResponseEntity<?> checkAvailability(
        @RequestParam String department,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
        @RequestParam LocalTime timeSlot) {

        boolean isAvailable = !reservationService.isConflict(department, date, timeSlot);
        return ResponseEntity.ok(isAvailable);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(
        @RequestBody ReservationRequestDto reservationDTO,
        @AuthenticationPrincipal CustomUser customUser) {

        User user = customUser.getUser();
        if (user == null) {
            return ResponseEntity.badRequest().body("사용자 정보가 유효하지 않습니다.");
        }

        if (reservationService.isConflict(reservationDTO.getDepartment(), reservationDTO.getDate(), reservationDTO.getTimeSlot())) {
            return ResponseEntity.badRequest().body("해당 시간대에는 선택된 진료과의 예약이 불가능합니다.");
        }

        reservationService.createAndSaveReservation(reservationDTO.getDepartment(), reservationDTO.getDate(), reservationDTO.getTimeSlot(), user, reservationDTO.getHospitalid());

        return ResponseEntity.ok("예약이 완료되었습니다.");
    }

    @GetMapping("/user")
    public ResponseEntity<List<ReservationRequestDto>> usersReservation(@AuthenticationPrincipal CustomUser customUser){

        return ResponseEntity.ok(reservationService.usersReservations(customUser));

    }

    @DeleteMapping("/{reservationId}")
    public ResponseEntity<String> deleteReservation(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reservationId) {
        reservationService.deleteReservation(customUser, reservationId);
        return ResponseEntity.ok("예약 취소 완료");
    }
}
