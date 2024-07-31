package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.service.HospitalService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // React 포트 연결
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals/all")
    public ResponseEntity<List<HospitalDTO>> getAllHospitalsWithoutPagination() {
        List<Hospital> hospitals = hospitalService.getAllHospitalsForMap();
        List<HospitalDTO> hospitalDTOs = hospitals.stream()
                .map(hospitalService::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(hospitalDTOs);
    }

    @GetMapping("/departments/detail")
    public ResponseEntity<Map<String, Object>> getAllHospitalDepartments(
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        Page<Hospital> hospitalPage = hospitalService.getAllHospitals(page, size);
        List<Hospital> hospitals = hospitalPage.getContent();
        Map<Long, HospitalDTO> hospitalDTOMap = new HashMap<>();

        for (Hospital hospital : hospitals) {
            HospitalDTO dto = new HospitalDTO(
                    hospital.getId(),
                    hospital.getName(),
                    hospital.getLatitude() != null ? hospital.getLatitude().doubleValue() : null,
                    hospital.getLongitude() != null ? hospital.getLongitude().doubleValue() : null,
                    hospital.getAddress(),
                    hospital.getDistrict(),
                    hospital.getSubDistrict(),
                    hospital.getTelephoneNumber(),
                    new ArrayList<>()
            );
            hospitalDTOMap.putIfAbsent(hospital.getId(), dto);
        }

        List<HospitalDepartmentDTO> departments = hospitalService.getAllHospitalDepartments();

        for (HospitalDepartmentDTO department : departments) {
            HospitalDTO hospitalDTO = hospitalDTOMap.get(department.getHospital().getId());
            if (hospitalDTO != null) {
                hospitalDTO.getDepartments().add(department.getDepartment());
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("content", new ArrayList<>(hospitalDTOMap.values()));
        response.put("currentPage", hospitalPage.getNumber());
        response.put("totalItems", hospitalPage.getTotalElements());
        response.put("totalPages", hospitalPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    private HospitalDTO convertToDto(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getLatitude() != null ? hospital.getLatitude().doubleValue() : null,
                hospital.getLongitude() != null ? hospital.getLongitude().doubleValue() : null,
                hospital.getAddress(),
                hospital.getDistrict(),
                hospital.getSubDistrict(),
                hospital.getTelephoneNumber(),
                new ArrayList<>()
        );
    }
}