package com.team5.hospital_here.location;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class GeocodingService {

    @Value("${naver.client.id}")
    private String clientId;

    @Value("${naver.client.secret}")
    private String clientSecret;

    private final WebClient webClient;

    public GeocodingService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://naveropenapi.apigw.ntruss.com").build();
    }

    public Mono<GeocodeResponseDto> getAddress(double lat, double lng) {
        return this.webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/map-reversegeocode/v2/gc")
                .queryParam("coords", lng + "," + lat)
                .queryParam("output", "json") // 출력 형식
                .build())
            .header("X-NCP-APIGW-API-KEY-ID", clientId)
            .header("X-NCP-APIGW-API-KEY", clientSecret)
            .retrieve()
            .bodyToMono(GeocodeResponseDto.class)
            .onErrorResume(e -> {
                e.printStackTrace();
                return Mono.empty();
            });
    }
}
