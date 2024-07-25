package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    public Page<Hospital> getAllHospitals(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hospitalRepository.findAll(pageable);
    }

    public Page<Hospital> searchHospitals(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return hospitalRepository.findByNameContaining(name, pageable);
    }
}