package com.team5.hospital_here.parsing.controller;

import com.team5.hospital_here.parsing.dto.WebScrapingDTO;
import com.team5.hospital_here.parsing.entity.WebScraping;
import com.team5.hospital_here.parsing.repository.WebScrapingRepository;
import com.team5.hospital_here.parsing.service.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/webscraping")
public class WebScrapingController {

    @Autowired
    private WebScrapingService webScrapingService;

    @PostMapping("/scrape")
    public ResponseEntity<String> scrapeData() {
        try {
            webScrapingService.scrapeAndSaveData();
            return ResponseEntity.ok("스크래핑 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("스크래핑 중 에러 " + e.getMessage());
        }
    }

    @GetMapping("/latest")
    public ResponseEntity<List<WebScrapingDTO>> getLatestDiseases() {
        try {
            List<WebScrapingDTO> diseases = webScrapingService.getLatestDiseases();
            return ResponseEntity.ok(diseases);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
