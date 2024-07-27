package com.team5.hospital_here.hospital.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class HospitalAIService {
    //FastAPI 서버에 요청

    private final WebClient webClient;
    @Autowired
    public HospitalAIService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8000").build();
    }

    public String getPrediction(String inputData) {
        return this.webClient.post()
            .uri("/predict")
            .bodyValue(new PredictRequest(inputData))
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }

    static class PredictRequest {
        private String data;

        public PredictRequest(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}

