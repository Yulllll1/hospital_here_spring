package com.team5.hospital_here.hospital.controller;

import com.team5.hospital_here.hospital.service.HospitalAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HospitalAIController {

    @Autowired
    private HospitalAIService hospitalAIService;

    @GetMapping("/symptoms/predict")
    public String getPrediction(@RequestParam String data) {
        return hospitalAIService.getPrediction(data);
    }
}

