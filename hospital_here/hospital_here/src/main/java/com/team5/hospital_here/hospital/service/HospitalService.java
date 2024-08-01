package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.Mapper.HospitalDepartmentMapper;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import com.team5.hospital_here.hospital.repository.HospitalDepartmentRepository;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    // Get all hospitals without pagination (for map view)
    public List<Hospital> getAllHospitalsForMap() {
        return hospitalRepository.findAll();
    }

    // Get paginated hospitals with optional name and address filters
    public Page<Hospital> getAllHospitals(String name, String address, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if ((name != null && !name.isEmpty()) || (address != null && !address.isEmpty())) {
            // Search by name and/or address
            return hospitalRepository.findByNameContainingIgnoreCaseAndAddressContainingIgnoreCase(name, address, pageable);
        }
        // Return all hospitals if no filters are provided
        return hospitalRepository.findAll(pageable);
    }

    // Search hospitals by name and address with pagination
    public Page<Hospital> searchHospitals(String name, String address, int page, int size) {
        return getAllHospitals(name, address, page, size);
    }

    // Get all hospital departments
    public List<HospitalDepartmentDTO> getAllHospitalDepartments() {
        return hospitalDepartmentRepository.findAll()
                .stream()
                .map(hospitalDepartmentMapper::convertToDto)
                .collect(Collectors.toList());
    }

    // Get departments by hospital ID
    public List<HospitalDepartmentDTO> getDepartmentsByHospitalId(Long hospitalId) {
        return hospitalDepartmentRepository.findByHospitalId(hospitalId)
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
                hospital.getTelephoneNumber(),
                new ArrayList<>()
        );
    }

    // Get all hospitals with their departments
    public List<HospitalDTO> getAllHospitalsWithDepartments() {
        // Get all hospitals
        List<Hospital> hospitals = hospitalRepository.findAll();
        // Get all departments
        List<HospitalDepartmentDTO> departments = hospitalDepartmentRepository.findAll()
                .stream()
                .map(hospitalDepartmentMapper::convertToDto)
                .collect(Collectors.toList());

        // Create a map to group departments by hospital
        Map<Long, HospitalDTO> hospitalDTOMap = new HashMap<>();

        // Convert hospitals to DTOs and initialize the map
        for (Hospital hospital : hospitals) {
            HospitalDTO dto = convertToDto(hospital);
            hospitalDTOMap.put(hospital.getId(), dto);
        }

        // Add departments to the corresponding hospital DTOs
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