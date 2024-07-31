package com.team5.hospital_here.chatMessage.gemini.dto;

import java.util.Set;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDTO {
    private String message; // GEMINI가 제공한 답변
    private Set<String> departments; // 추천 진료과 목록
}
