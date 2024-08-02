package com.team5.hospital_here.chatMessage.gemini;

import com.team5.hospital_here.chatMessage.gemini.dto.GeminiRequestDTO;
import com.team5.hospital_here.chatMessage.gemini.dto.GeminiResponseDTO;
import com.team5.hospital_here.chatMessage.gemini.dto.ResponseDTO;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${GEMINI.API.URL}")
    private String url;

    @Value("${GEMINI.API.KEY}")
    private String key;

    private final RestTemplate restTemplate;
//    private final DepartmentRepository departmentRepository;

    public ResponseDTO prompt(GeminiRequestDTO geminiRequestDTO) {
        String requestUrl = url + "?key=" + key;

        GeminiResponseDTO response;
        try {
            response = restTemplate.postForObject(requestUrl, geminiRequestDTO, // GEMINI로 요청 전송
                GeminiResponseDTO.class);

            if (response == null) {
                throw new RuntimeException("Gemini 응답 데이터 없음");
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("Gemini 요청 에러");
        }

        return parseResponse(response);
    }

    // GEMINI가 제공한 답변에서 진료과 뽑아내기
    private ResponseDTO parseResponse(GeminiResponseDTO geminiResponseDTO) {
        // DB상 진료과 목록
        List<String> tokens = List.of("내과", "신경과", "정신건강의학과", "외과", "정형외과", "신경외과", "심장혈관흉부외과",
            "성형외과", "마취통증의학과", "산부인과", "소아청소년과", "안과", "이비인후과", "피부과", "비뇨의학과", "영상의학과", "방사선종양학과",
            "병리과", "진단검사의학과", "결핵과", "재활의학과", "핵의학과", "가정의학과", "응급의학과", "직업환경의학과", "예방의학과", "치과",
            "구강악안면외과", "치과보철과", "치과교정과", "소아치과", "치주과", "치과보존과", "구강내과", "영상치의학과", "구강병리과", "예방치과",
            "통합치의학과", "한방내과", "한방부인과", "한방소아과", "한방안재재·이비인후·피부과", "한방신경정신과", "침구과", "한방재활의학과",
            "사상체질과", "한방응급");

        // GEMNI가 제공한 답변
        String resultMessage = geminiResponseDTO.getCandidates().get(0).getContent().getParts()
            .get(0).getText();

        // 쌍느낌표(!!)로 감싸진 진료과 텍스트 추출
        Set<String> departments = new HashSet<>();
        for (int i = 0; i < resultMessage.length(); i++) {
            char c = resultMessage.charAt(i);
            if (c == '!') {
                i += 2;

                StringBuilder token = new StringBuilder();
                for (int j = i; j < resultMessage.length(); j++) {
                    if (resultMessage.charAt(j) == '!') {
                        departments.add(token.toString());
                        i = j + 2;
                        break;
                    }

                    token.append(resultMessage.charAt(j));
                }
            }
        }

        return ResponseDTO.builder()
            .message(resultMessage).departments(departments).build();
    }

    @Data
    @Builder
    static class Response {
        private String message;
        private List<String> departments;
    }
}
