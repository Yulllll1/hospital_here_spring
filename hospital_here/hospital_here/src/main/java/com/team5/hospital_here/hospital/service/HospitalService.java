package com.team5.hospital_here.hospital.service;

import com.team5.hospital_here.hospital.entity.Hospital;
import com.team5.hospital_here.hospital.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    public List<Hospital> searchHospitalsByName(String name) {
        return hospitalRepository.findByNameContaining(name);
    }

    public List<Hospital> searchHospitalsByCity(String city) {
        return hospitalRepository.findByCity(city);
    }
}