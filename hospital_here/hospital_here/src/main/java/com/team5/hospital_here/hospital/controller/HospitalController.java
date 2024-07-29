package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.dto.HospitalDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.service.HospitalService;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // react port 연결
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    public Page<Hospital> getAllHospitals(@RequestParam("page") int page, @RequestParam("size") int size) {
        return hospitalService.getAllHospitals(page, size);
    }

    @GetMapping("/search")
    public Page<Hospital> searchHospitals(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        return hospitalService.searchHospitals(name, page, size);
    }

    @GetMapping("/hospitals/{id}")
    public ResponseEntity<HospitalDto> getHospitalById(@PathVariable Long id) {
        Hospital hospital = hospitalService.getHospitalById(id);
        HospitalDto hospitalDto = new HospitalDto(
                hospital.getId(),
                hospital.getName(),
                hospital.getLatitude() != null ? hospital.getLatitude().doubleValue() : null,
                hospital.getLongitude() != null ? hospital.getLongitude().doubleValue() : null,
                hospital.getAddress()
        );
        return ResponseEntity.ok(hospitalDto);
    }

}