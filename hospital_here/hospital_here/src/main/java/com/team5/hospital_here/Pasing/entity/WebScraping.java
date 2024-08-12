package com.team5.hospital_here.Pasing.entity;

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

    //데이터 초기화를 위해 생성자 추가
    public WebScraping() {}

    public WebScraping(String diseaseName) {
        this.diseaseName = diseaseName;
        this.createdAt = LocalDateTime.now();
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

}
