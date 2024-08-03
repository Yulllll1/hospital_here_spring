package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
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


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000") // React 포트 연결
public class HospitalController {

    @Autowired
    private HospitalService hospitalService;


    //병원 정보 + 부서별 한번에 보기
    @GetMapping("/hospitals/all")
    public ResponseEntity<List<HospitalDTO>> getAllHospitalsWithoutPagination() {
        List<HospitalDTO> hospitalDTOs = hospitalService.getAllHospitalsWithDepartments();
        return ResponseEntity.ok(hospitalDTOs);
    }

    //병원 정보 + 검색
    @GetMapping("/search")
    public ResponseEntity<Map<String, Object>> searchHospitals(
            @RequestParam(value = "name", defaultValue = "") String name,
            @RequestParam(value = "address", defaultValue = "") String address,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        // 병원 검색 및 DTO 변환
        Page<Hospital> hospitalPage = hospitalService.searchHospitals(name, address, page, size);
        List<Hospital> hospitals = hospitalPage.getContent();
        Map<Long, HospitalDTO> hospitalDTOMap = new HashMap<>();

        for (Hospital hospital : hospitals) {
            HospitalDTO dto = hospitalService.convertToDto(hospital);
            hospitalDTOMap.putIfAbsent(hospital.getId(), dto);
        }

        //병원 부서 정보 추가
        List<HospitalDepartmentDTO> departments = hospitalService.getAllHospitalDepartments();

        for (HospitalDepartmentDTO department : departments) {
            HospitalDTO hospitalDTO = hospitalDTOMap.get(department.getHospital().getId());
            if (hospitalDTO != null) {
                hospitalDTO.getDepartments().add(department.getDepartment());
            }
        }

        //응답
        Map<String, Object> response = new HashMap<>();
        response.put("content", new ArrayList<>(hospitalDTOMap.values()));
        response.put("currentPage", hospitalPage.getNumber());
        response.put("totalItems", hospitalPage.getTotalElements());
        response.put("totalPages", hospitalPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/hospitals/contained-name")
    public ResponseEntity<List<Hospital>> getHospitalsByContainedName(@RequestParam String name){
        System.out.println(name);
        return ResponseEntity.ok(hospitalService.getHospitalByNameContained(name));
    }

    @GetMapping("/hospitals/{hospitalId}")
    public ResponseEntity<HospitalDTO> getHospitalById(@PathVariable Long hospitalId) {
        return ResponseEntity.ok(hospitalService.getHospitalDTOById(hospitalId));
    }
}