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

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Autowired
    private HospitalDepartmentMapper hospitalDepartmentMapper;

    public Page<Hospital> searchHospitals(String name, String address, String departmentName, Double latitude, Double longitude, int page, int size) {
        List<Hospital> filteredHospitals = hospitalRepository.searchHospitals(name, address, departmentName);

        if (latitude != null && longitude != null) {
            filteredHospitals.sort((h1, h2) -> {
                Double distance1 = calculateDistanceOrNull(latitude, longitude, h1.getLatitude(), h1.getLongitude());
                Double distance2 = calculateDistanceOrNull(latitude, longitude, h2.getLatitude(), h2.getLongitude());

                if (distance1 == null && distance2 == null) return 0;
                if (distance1 == null) return 1;
                if (distance2 == null) return -1;

                return Double.compare(distance1, distance2);
            });
        }

        int start = (int) PageRequest.of(page, size).getOffset();
        int end = Math.min((start + PageRequest.of(page, size).getPageSize()), filteredHospitals.size());

        List<Hospital> paginatedHospitals = filteredHospitals.subList(start, end);

        return new PageImpl<>(paginatedHospitals, PageRequest.of(page, size, Sort.by("id").ascending()), filteredHospitals.size());
    }

    private Double calculateDistanceOrNull(Double userLat, Double userLon, Double hospitalLat, Double hospitalLon) {
        if (hospitalLat == null || hospitalLon == null) {
            return null;
        }
        return calculateDistance(userLat, userLon, hospitalLat, hospitalLon);
    }

    private double calculateDistance(double userLat, double userLon, double hospitalLat, double hospitalLon) {
        final int R = 6371;
        double latDistance = Math.toRadians(hospitalLat - userLat);
        double lonDistance = Math.toRadians(hospitalLon - userLon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(hospitalLat))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
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
            dto.setDepartments(new ArrayList<>());
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