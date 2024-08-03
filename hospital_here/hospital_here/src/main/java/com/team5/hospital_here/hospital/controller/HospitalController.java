package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.Mapper.DepartmentMapper;
import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.service.DepartmentService;
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

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentMapper departmentMapper;


    @GetMapping("/hospitals/all")
    public ResponseEntity<List<HospitalDTO>> getAllHospitalsWithoutPagination() {
        List<HospitalDTO> hospitalDTOs = hospitalService.getAllHospitalsWithDepartments();
        return ResponseEntity.ok(hospitalDTOs);
    }

    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchHospitals(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam(value = "departmentName", defaultValue = "") String departmentName,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        // 병원 검색 및 DTO 변환
        Page<Hospital> hospitalPage = hospitalService.searchHospitals(name, address, departmentName, page, size);
        List<Hospital> hospitals = hospitalPage.getContent();
        Map<Long, HospitalDTO> hospitalDTOMap = new HashMap<>();

        for (Hospital hospital : hospitals) {
            HospitalDTO dto = hospitalService.convertToDto(hospital);
            hospitalDTOMap.putIfAbsent(hospital.getId(), dto);
        }

        // 병원 ID 기반으로 부서 정보 추가
        for (Long hospitalId : hospitalDTOMap.keySet()) {
            List<Department> departments = departmentService.getDepartmentsByHospitalId(hospitalId);
            HospitalDTO hospitalDTO = hospitalDTOMap.get(hospitalId);
            if (hospitalDTO != null) {
                // Department 객체를 DepartmentDTO 객체로 변환
                List<DepartmentDTO> departmentDTOs = departments.stream()
                        .map(department -> departmentMapper.convertToDto(department))
                        .collect(Collectors.toList());
                hospitalDTO.setDepartments(departmentDTOs);
            }
        }

        // 응답
        Map<String, Object> response = new HashMap<>();
        response.put("content", new ArrayList<>(hospitalDTOMap.values()));
        response.put("currentPage", hospitalPage.getNumber());
        response.put("totalItems", hospitalPage.getTotalElements());
        response.put("totalPages", hospitalPage.getTotalPages());

        return ResponseEntity.ok(response);
    }
}