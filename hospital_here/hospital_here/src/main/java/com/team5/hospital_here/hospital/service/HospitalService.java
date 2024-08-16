package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.Mapper.HospitalDepartmentMapper;
import com.team5.hospital_here.hospital.dto.HospitalDTO;
import com.team5.hospital_here.hospital.dto.HospitalDepartmentDTO;
import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.entity.HospitalDepartment;
import com.team5.hospital_here.hospital.repository.HospitalDepartmentRepository;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class HospitalService {

    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private HospitalDepartmentRepository hospitalDepartmentRepository;

    @Autowired
    private HospitalDepartmentMapper hospitalDepartmentMapper;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public Page<Hospital> searchHospitals(String name, String address, String departmentName, List<String> departmentNames, Double latitude, Double longitude, int page, int size) {
        List<Hospital> filteredHospitals = hospitalRepository.searchHospitals(name, address, departmentName, departmentNames);

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

    public HospitalDTO convertToDtoWithOpenStatus(Hospital hospital, Double distance) {
        HospitalDTO dto = convertToDto(hospital, distance);
        dto.setOpenNow(isOpenNow(dto));
        return dto;
    }

    private Double calculateDistanceOrNull(Double userLat, Double userLon, Double hospitalLat, Double hospitalLon) {
        if (hospitalLat == null || hospitalLon == null) {
            return null;
        }
        return calculateDistance(userLat, userLon, hospitalLat, hospitalLon);
    }

    public double calculateDistance(double userLat, double userLon, double hospitalLat, double hospitalLon) {
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
        return hospitalDepartmentMapper.convertToDto(hospital);
    }

    //distance 설정
    public HospitalDTO convertToDto(Hospital hospital, Double distance) {
        HospitalDTO dto = convertToDto(hospital);
        dto.setDistance(distance);
        return dto;
    }

    public List<HospitalDTO> getAllHospitalsWithDepartmentsAndOpenStatus() {
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

        // 모든 DTO에 대해 isOpenNow 속성 설정
        for (HospitalDTO dto : hospitalDTOMap.values()) {
            dto.setOpenNow(isOpenNow(dto));
        }

        return new ArrayList<>(hospitalDTOMap.values());
    }

    public Hospital getHospitalById(Long id) {
        return hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found for id :: " + id));
    }

    public HospitalDTO getHospitalDTOById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hospital not found for id :: " + id));
        HospitalDTO hospitalDTO = convertToDto(hospital);
        return hospitalDTO;
    }

    public List<Hospital> getHospitalByNameContained(String name){
        List<Hospital> hospitals = hospitalRepository.findByNameContains(name);
        for(Hospital hospital : hospitals){
            for(HospitalDepartment hospitalDepartment : hospital.getHospitalDepartments()){
                hospitalDepartment.setHospital(null);
            }
        }

        return hospitals;
    }

    public boolean isOpenNow(HospitalDTO hospitalDTO) {
        LocalDateTime now = LocalDateTime.now();
        DayOfWeek currentDay = now.getDayOfWeek();
        LocalTime currentTime = now.toLocalTime();

        LocalTime startTime = null;
        LocalTime endTime = null;

        // 현재 요일에 맞는 영업 시작 및 종료 시간 파싱
        switch (currentDay) {
            case MONDAY:
                startTime = parseTime(hospitalDTO.getMonStartTime());
                endTime = parseTime(hospitalDTO.getMonEndTime());
                break;
            case TUESDAY:
                startTime = parseTime(hospitalDTO.getTueStartTime());
                endTime = parseTime(hospitalDTO.getTueEndTime());
                break;
            case WEDNESDAY:
                startTime = parseTime(hospitalDTO.getWedStartTime());
                endTime = parseTime(hospitalDTO.getWedEndTime());
                break;
            case THURSDAY:
                startTime = parseTime(hospitalDTO.getThuStartTime());
                endTime = parseTime(hospitalDTO.getThuEndTime());
                break;
            case FRIDAY:
                startTime = parseTime(hospitalDTO.getFriStartTime());
                endTime = parseTime(hospitalDTO.getFriEndTime());
                break;
            case SATURDAY:
                startTime = parseTime(hospitalDTO.getSatStartTime());
                endTime = parseTime(hospitalDTO.getSatEndTime());
                break;
            case SUNDAY:
                startTime = parseTime(hospitalDTO.getSunStartTime());
                endTime = parseTime(hospitalDTO.getSunEndTime());
                break;
        }

        // 디버깅 출력
        System.out.println("Current time: " + currentTime);
        System.out.println("Start time: " + startTime);
        System.out.println("End time: " + endTime);

        if (startTime != null && endTime != null) {
            boolean isOpen = !currentTime.isBefore(startTime) && !currentTime.isAfter(endTime);
            System.out.println("Is open: " + isOpen);
            return isOpen;
        }

        return false;
    }

    private LocalTime parseTime(String timeString) {
        System.out.println("Parsing time string: " + timeString);
        try {
            return timeString != null ? LocalTime.parse(timeString, TIME_FORMATTER) : null;
        } catch (DateTimeParseException e) {
            System.err.println("Failed to parse time: " + timeString);
            // 예외 처리를 위한 로직을 추가하거나 기본값을 설정합니다.
            return null;
        }
    }

}