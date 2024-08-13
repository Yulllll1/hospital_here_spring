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

    @Value("${juso.api.key}")
    private String jusoApiKey;
    //비동기식 처리
    private final WebClient webClient;

    public GeocodingService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://naveropenapi.apigw.ntruss.com").build();
    }

    // 도로명 주소를 위도와 경도로 변환
    public Mono<GeocodeResponseDto> getCoords(String address) {
        return this.webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/map-geocode/v2/geocode")
                .queryParam("query", address)
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

    // 위도와 경도를 주소로 변환
    public Mono<ReverseGeocodeResponseDto> getAddress(double lat, double lng) {
        return this.webClient.get()
            .uri(uriBuilder -> uriBuilder
                .path("/map-reversegeocode/v2/gc")
                .queryParam("coords", lng + "," + lat)
                .queryParam("output", "json") // 출력 형식
                .build())
            .header("X-NCP-APIGW-API-KEY-ID", clientId)
            .header("X-NCP-APIGW-API-KEY", clientSecret)
            .retrieve()
            .bodyToMono(ReverseGeocodeResponseDto.class)
            .onErrorResume(e -> {
                e.printStackTrace();
                return Mono.empty();
            });
    }

    public Mono<AddressResponseDto> searchAddress(String address, int currentPage) {
        return WebClient.create("https://www.juso.go.kr/addrlink/addrLinkApi.do")
            .get()
            .uri(uriBuilder -> uriBuilder
                .queryParam("confmKey", jusoApiKey)
                .queryParam("currentPage", currentPage)
                .queryParam("countPerPage", 10)
                .queryParam("keyword", address)
                .queryParam("resultType", "json")
                .build())
            .retrieve()
            .bodyToMono(AddressResponseDto.class)
            .onErrorResume(e -> {
                e.printStackTrace();
                return Mono.empty();
            });
    }
}
