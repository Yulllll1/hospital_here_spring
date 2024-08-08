package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.Mapper.DepartmentMapper;
import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.service.HospitalService;

import java.util.*;
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

    //hospital + department 정보
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
            @RequestParam(value = "latitude", required = false) Double latitude,
            @RequestParam(value = "longitude", required = false) Double longitude,
            @RequestParam("page") int page,
            @RequestParam("size") int size) {

        //검색 결과를 hospitalDTO로 변환 후 hospitaldtomap에 저장
        Page<Hospital> hospitalPage = hospitalService.searchHospitals(name, address, departmentName, latitude, longitude, page, size);
        List<Hospital> hospitals = hospitalPage.getContent();
        Map<Long, HospitalDTO> hospitalDTOMap = new HashMap<>();

        // Hospital을 HospitalDTO로 변환하면서 distance를 설정
        for (Hospital hospital : hospitals) {
            Double distance = null;
            if (latitude != null && longitude != null && hospital.getLatitude() != null && hospital.getLongitude() != null) {
                distance = hospitalService.calculateDistance(latitude, longitude, hospital.getLatitude(), hospital.getLongitude());
            }
            HospitalDTO dto = hospitalService.convertToDto(hospital, distance);
            hospitalDTOMap.putIfAbsent(hospital.getId(), dto);
        }

        // 병원 ID 기반으로 부서 정보 추가
        //부서 목록을 가져와서 departmentdto로 변환 후 hospitaldto에 저장
        for (Long hospitalId : hospitalDTOMap.keySet()) {
            List<Department> departments = departmentService.getDepartmentsByHospitalId(hospitalId);
            HospitalDTO hospitalDTO = hospitalDTOMap.get(hospitalId);
            if (hospitalDTO != null) {
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
