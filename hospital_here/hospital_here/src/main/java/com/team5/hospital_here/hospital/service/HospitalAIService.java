package com.team5.hospital_here.hospital.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HospitalAIService {

    @Value("${gemini.api.key}")
    private String apiKey;

    @Value("${gemini.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public HospitalAIService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getRecommendations(String symptom) {
        String url = String.format("%s?symptom=%s&api_key=%s", apiUrl, symptom, apiKey);
        
        return restTemplate.getForObject(url, String.class);
    }
}
