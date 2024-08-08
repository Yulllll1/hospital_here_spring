package com.team5.hospital_here.location;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping("/api/geocode")
@RestController
public class GeocodingController {

    private final GeocodingService geocodingService;

    public GeocodingController(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    @GetMapping("/coords-to-address")
    public Mono<ReverseGeocodeResponseDto> getAddressByCoords(
        @RequestParam double lat,
        @RequestParam double lng) {
        return geocodingService.getAddress(lat, lng);
    }

    @GetMapping("/address-to-coords")
    public Mono<GeocodeResponseDto> getCoordsByAddress(@RequestParam String address) {
        return geocodingService.getCoords(address);
    }
}
