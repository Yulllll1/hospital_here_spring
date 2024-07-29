package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.service.HospitalAIService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // React 기본 포트
public class HospitalAIController {

    private final HospitalAIService hospitalAIService;

    public HospitalAIController(HospitalAIService hospitalAIService) {
        this.hospitalAIService = hospitalAIService;
    }

    @GetMapping("/recommendation")
    public String getRecommendation(@RequestParam String symptom) {
        return hospitalAIService.getRecommendations(symptom);
    }
}

