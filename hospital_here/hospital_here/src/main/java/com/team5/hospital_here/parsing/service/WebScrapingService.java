package com.team5.hospital_here.parsing.service;

import com.team5.hospital_here.parsing.dto.WebScrapingDTO;
import com.team5.hospital_here.parsing.entity.WebScraping;
import com.team5.hospital_here.parsing.repository.WebScrapingRepository;
import jakarta.annotation.PostConstruct;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class WebScrapingService {

    private static final Logger logger = LoggerFactory.getLogger(WebScrapingService.class);

    @Autowired
    private WebScrapingRepository webScrapingRepository;

    @Scheduled(cron = "* 0 12 * * MON") // 월요일 12시에 스크래핑
    public void scrapeAndSaveData() {
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

                WebScraping webScraping = new WebScraping(cleanedDiseaseName, (long) (count + 1));
                webScrapingRepository.save(webScraping);
                count++;
            }

            logger.info("Web scraping 성공");

        } catch (IOException e) {
            logger.error("에러가 났습니다.", e);
        }
    }

    private String cleanDiseaseName(String rawName) {
        int bracketIndex = rawName.indexOf("[");

        if (bracketIndex == -1) {
            return rawName.trim();
        }

        return rawName.substring(0, bracketIndex).trim();
    }

    @PostConstruct
    public void init() {
        scrapeAndSaveData();
    }

    public List<WebScrapingDTO> getLatestDiseases() {
        List<WebScraping> webScrapings = webScrapingRepository.findScrapingData(); // rank 기준으로 정렬
        return webScrapings.stream()
                .map(ws -> new WebScrapingDTO(ws.getId(), ws.getDiseaseName(), ws.getCreatedAt(), ws.getRank()))
                .collect(Collectors.toList());
    }
}
