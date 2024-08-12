package com.team5.hospital_here.chatMessage.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GeminiRequestDTO {
    private Content contents;
    private final GenerationConfig generationConfig = new GenerationConfig();

    public GeminiRequestDTO(Content contents) {
        this.contents = contents;
    }

    @Data
    @AllArgsConstructor
    public static class Content {
        private Part parts;
    }

    @Data
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    @Data
    public static class GenerationConfig {
        private final Integer maxOutputTokens = 1000; // 응답 토큰 수 제한
        private final Integer candidateCount = 1; // 응답 수
    }
}
