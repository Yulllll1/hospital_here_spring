package com.team5.hospital_here.pasing.controller;

import com.team5.hospital_here.pasing.service.WebScrapingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/webscraping")
public class WebScrapingController {

    @Autowired
    private WebScrapingService webScrapingService;

    @PostMapping("/scrape")
    public ResponseEntity<String> scrapeData() {
        try {
            webScrapingService.scrapeAndSaveData();
            return ResponseEntity.ok("Scraping completed successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during scraping: " + e.getMessage());
        }
    }
}