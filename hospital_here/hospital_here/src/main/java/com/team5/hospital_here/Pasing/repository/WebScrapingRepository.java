package com.team5.hospital_here.Pasing.repository;

import com.team5.hospital_here.Pasing.entity.WebScraping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WebScrapingRepository extends JpaRepository<WebScraping, Long> {

    // 최신 데이터 가져오기
    Optional<WebScraping> findTopByOrderByCreatedAtDesc();

    // 모든 데이터 가져오기
    List<WebScraping> findAll();
}