package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.Mapper.HospitalDepartmentMapper;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.repository.HospitalDepartmentRepository;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Autowired
    private HospitalDepartmentMapper hospitalDepartmentMapper;

    public Page<Hospital> searchHospitals(String name, String address, String departmentName, int page, int size) {
        // 조건에 맞는 병원 목록 검색
        List<Hospital> filteredHospitals = hospitalRepository.searchHospitals(name, address, departmentName);

        // 페이지네이션 적용
        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + PageRequest.of(page, size).getPageSize()), filteredHospitals.size());

        List<Hospital> paginatedHospitals = filteredHospitals.subList(start, end);

        return new PageImpl<>(paginatedHospitals, PageRequest.of(page, size, Sort.by("id").ascending()), filteredHospitals.size());
    }

    public Page<Hospital> searchHospitals(String name, String address, String departmentName, int page, int size) {
        return getAllHospitals(name, address, page, size);
    }


    public List<HospitalDepartmentDTO> getAllHospitalDepartments() {
        return hospitalDepartmentRepository.findAll()
                .stream()
                .map(hospitalDepartmentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    public HospitalDTO convertToDto(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getLatitude() != null ? hospital.getLatitude() : null,
                hospital.getLongitude() != null ? hospital.getLongitude() : null,
                hospital.getAddress(),
                hospital.getDistrict(),
                hospital.getSubDistrict(),
                hospital.getTelephoneNumber(),
                new ArrayList<>()
        );
    }

    public List<HospitalDTO> getAllHospitalsWithDepartments() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<HospitalDepartmentDTO> departments = hospitalDepartmentRepository.findAll()
                .stream()
                .map(hospitalDepartmentMapper::convertToDto)
                .toList();

        Map<Long, HospitalDTO> hospitalDTOMap = new HashMap<>();

        for (Hospital hospital : hospitals) {
            HospitalDTO dto = convertToDto(hospital);
            dto.setDepartments(new ArrayList<>()); // departments 필드 초기화
            hospitalDTOMap.put(hospital.getId(), dto);
        }

        for (HospitalDepartmentDTO department : departments) {
            HospitalDTO hospitalDTO = hospitalDTOMap.get(department.getHospital().getId());
            if (hospitalDTO != null) {
                hospitalDTO.getDepartments().add(department.getDepartment());
            }
        }

        return new ArrayList<>(hospitalDTOMap.values());
    }

    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found for id :: " + id));
    }


}