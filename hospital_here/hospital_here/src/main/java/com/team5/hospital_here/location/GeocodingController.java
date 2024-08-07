package com.team5.hospital_here.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/api/geocode")
    public Mono<GeocodeResponseDto> getGeocode(
        @RequestParam double lat,
        @RequestParam double lng) {
        return geocodingService.getAddress(lat, lng);
    }
}
