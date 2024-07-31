package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
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
@CrossOrigin(origins = "http://localhost:3000") // React 포트 연결
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    public ResponseEntity<Page<HospitalDTO>> getAllHospitals(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Hospital> hospitalsPage = hospitalService.getAllHospitals(page, size);
        Page<HospitalDTO> hospitalsDTOPage = hospitalsPage.map(hospitalService::convertToDto);
        return ResponseEntity.ok(hospitalsDTOPage);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<HospitalDTO>> searchHospitals(@RequestParam("name") String name, @RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Hospital> hospitalsPage = hospitalService.searchHospitals(name, page, size);
        Page<HospitalDTO> hospitalsDTOPage = hospitalsPage.map(hospitalService::convertToDto);
        return ResponseEntity.ok(hospitalsDTOPage);
    }

    @GetMapping("/hospitals/all")
    public ResponseEntity<List<HospitalDTO>> getAllHospitalsWithoutPagination() {
        List<Hospital> hospitals = hospitalService.getAllHospitalsForMap();
        List<HospitalDTO> hospitalDTOs = hospitals.stream()
                .map(hospitalService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hospitalDTOs);
    }

    @GetMapping("/departments/detail")
    public ResponseEntity<List<HospitalDepartmentDTO>> getAllHospitalDepartments() {
        List<HospitalDepartmentDTO> departments = hospitalService.getAllHospitalDepartments();
        return ResponseEntity.ok(departments);
    }
}