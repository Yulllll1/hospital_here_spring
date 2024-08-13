package com.team5.hospital_here.location;

import org.springframework.http.ResponseEntity;
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
    // 도로명 주소 검색
    public Mono<ResponseEntity<AddressResponseDto>> searchAddress(@RequestParam String address) {
        return geocodingService.searchAddress(address)
            .map(response -> ResponseEntity.ok(response))
            .defaultIfEmpty(ResponseEntity.notFound().build());
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
