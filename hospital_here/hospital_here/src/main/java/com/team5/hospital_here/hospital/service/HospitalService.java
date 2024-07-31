package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.Mapper.HospitalDepartmentMapper;
import com.team5.hospital_here.hospital.dto.DepartmentDTO;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import com.team5.hospital_here.hospital.repository.DepartmentRepository;
import com.team5.hospital_here.hospital.repository.HospitalDepartmentRepository;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Autowired
    private HospitalDepartmentMapper hospitalDepartmentMapper;

    // Paginated retrieval of hospitals
    public Page<Hospital> getAllHospitals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hospitalRepository.findAll(pageable);
    }

    // Search hospitals by name with pagination
    public Page<Hospital> searchHospitals(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hospitalRepository.findByNameContaining(name, pageable);
    }

    // Get all hospitals without pagination (for map view)
    public List<Hospital> getAllHospitalsForMap() {
        return hospitalRepository.findAll();
    }

    // Get a specific hospital by ID
    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found for id :: " + id));
    }

    // Get departments by hospital ID
    public List<HospitalDepartmentDTO> getDepartmentsByHospitalId(Long hospitalId) {
        return hospitalDepartmentRepository.findByHospitalId(hospitalId)
                .stream()
                .map(hospitalDepartmentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    // Get all hospital departments
    public List<HospitalDepartmentDTO> getAllHospitalDepartments() {
        return hospitalDepartmentRepository.findAll()
                .stream()
                .map(hospitalDepartmentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    // Convert Hospital entity to HospitalDTO
    public HospitalDTO convertToDto(Hospital hospital) {
        return new HospitalDTO(
                hospital.getId(),
                hospital.getName(),
                hospital.getLatitude() != null ? hospital.getLatitude().doubleValue() : null,
                hospital.getLongitude() != null ? hospital.getLongitude().doubleValue() : null,
                hospital.getAddress(),
                hospital.getDistrict(),
                hospital.getSubDistrict(),
                hospital.getTelephoneNumber()
        );
    }
}