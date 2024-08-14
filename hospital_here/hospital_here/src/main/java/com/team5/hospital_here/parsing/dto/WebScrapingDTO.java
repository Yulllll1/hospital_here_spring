package com.team5.hospital_here.parsing.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class WebScrapingDTO {
    // Getters and setters
    private Long id;
    private String diseaseName;
    private LocalDateTime createdAt;
    private Long rank;

    public WebScrapingDTO(Long id, String diseaseName, LocalDateTime createdAt, Long rank) {
        this.id = id;
        this.diseaseName = diseaseName;
        this.createdAt = createdAt;
        this.rank = rank;
    }

}