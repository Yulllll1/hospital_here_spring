package com.team5.hospital_here.parsing.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Table(name = "webscraping")
@Entity
public class WebScraping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "disease_name", nullable = false)
    private String diseaseName;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "disease_rank")
    private Long rank;

    public WebScraping() {}

    public WebScraping(String diseaseName, Long rank) {
        this.diseaseName = diseaseName;
        this.createdAt = LocalDateTime.now();
        this.rank = rank;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
