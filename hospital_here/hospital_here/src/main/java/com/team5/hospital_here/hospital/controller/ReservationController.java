package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.hospital.dto.ReservationRequestDto;
import com.team5.hospital_here.hospital.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping
    public ResponseEntity<?> createReservation(
        @RequestBody ReservationRequestDto reservationDTO,
        @AuthenticationPrincipal CustomUser customUser) {

        Long userId = (customUser != null) ? customUser.getUser().getId() : -1L;

        if (reservationService.isConflict(reservationDTO.getDepartment(), reservationDTO.getDate(), reservationDTO.getTimeSlot())) {
            return ResponseEntity.badRequest().body("해당 시간대에는 선택된 진료과의 예약이 불가능합니다.");
        }

        reservationService.createAndSaveReservation(reservationDTO.getDepartment(), reservationDTO.getDate(), reservationDTO.getTimeSlot(), userId);

        return ResponseEntity.ok("예약이 완료되었습니다.");
    }
}
