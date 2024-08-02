package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.hospital.dto.ReservationRequestDto;
import com.team5.hospital_here.hospital.entity.Reservation;
import com.team5.hospital_here.hospital.service.ReservationService;
import java.util.List;
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


        Long userId;
        if (customUser != null) {
            userId = customUser.getUser().getId();
        } else {
            // 임의의 유저 ID 생성 (여기서는 예시로 -1 사용, 실제로는 유저 생성 로직을 추가해야 함)
            userId = -1L;
        }
        // 해당 날짜와 시간에 선택한 진료과가 이미 예약 되었는지 확인
        List<Reservation> existingReservations = reservationService.getReservations(
            reservationDTO.getDate(),
            reservationDTO.getTimeSlot()
        );

        boolean isConflict = existingReservations.stream()
            .anyMatch(reservation -> reservationDTO.getDepartment().equals(reservation.getDepartment()));

        if (isConflict) {
            return ResponseEntity.badRequest().body("해당 시간대에는 선택된 진료과의 예약이 불가능합니다.");
        }

        Reservation reservation = reservationService.createReservation(
            reservationDTO.getDepartment(),
            reservationDTO.getDate(),
            reservationDTO.getTimeSlot(),
            userId
        );
        reservationService.save(reservation);

        return ResponseEntity.ok("예약이 완료되었습니다.");
    }
}
