package com.team5.hospital_here.pasing.service;

import com.team5.hospital_here.pasing.entity.WebScraping;
import com.team5.hospital_here.pasing.repository.WebScrapingRepository;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class  WebScrapingService {

    private static final Logger logger = LoggerFactory.getLogger(WebScrapingService.class);

    @Autowired
    private WebScrapingRepository webScrapingRepository;

    @Scheduled(cron = "* 0 12 * * MON") // 월요일 12시에 스크래핑
    public void scrapeAndSaveData() {
        logger.info("Starting scheduled web scraping task...");

        try {
            // 데이터베이스에서 모든 데이터 삭제
            webScrapingRepository.deleteAll();

            // 웹 페이지에서 데이터 가져오기
            Document doc = Jsoup.connect("https://terms.naver.com/list.naver?cid=42298&categoryId=42298").get();
            Elements titleElements = doc.select("div.subject strong.title");

            // 5개의 항목만 처리 (첫 번째는 제외)
            int count = 0;
            for (int i = 1; i < titleElements.size(); i++) {
                if (count >= 5) {
                    break;
                }

                Element element = titleElements.get(i);
                String rawDiseaseName = element.text();
                String cleanedDiseaseName = cleanDiseaseName(rawDiseaseName);

                WebScraping webScraping = new WebScraping(cleanedDiseaseName);
                webScrapingRepository.save(webScraping);
                count++;
            }

            logger.info("Web scraping task completed successfully.");

        } catch (IOException e) {
            logger.error("Error during web scraping", e);
        }
    }

    private String cleanDiseaseName(String rawName) {
        // "[" 문자의 위치를 찾습니다.
        int bracketIndex = rawName.indexOf("[");

        // "[" 문자가 없다면 원래 문자열을 반환합니다.
        if (bracketIndex == -1) {
            return rawName.trim();
        }

        // "[" 문자 이전의 모든 문자열을 가져옵니다.
        String cleanedDiseaseName = rawName.substring(0, bracketIndex).trim();

        return cleanedDiseaseName;
    }


    @PostConstruct
    public void init() {
        // 애플리케이션 시작 시 스크래핑 실행
        scrapeAndSaveData();
    }

    public WebScraping getLatestData() {
        return webScrapingRepository.findTopByOrderByCreatedAtDesc()
                .orElseThrow(() -> new RuntimeException("No data found"));
    }
}