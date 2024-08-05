package com.team5.hospital_here.chatMessage.gemini.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class GeminiRequestDTO {

    private Content contents;
    private final GenerationConfig generationConfig = new GenerationConfig();

    /**
     *
     * @param message 사용자가 입력한 메시지
     */
    public GeminiRequestDTO(String message) {
        String prompt = "'" + message
            + "'에 대한 소견을 ['내과','신경과','정신건강의학과',"  // 여기서 부터 병원 진료과 데이터 배열
            + "'외과','정형외과','신경외과','심장혈관흉부외과','성형외과'," // 추후 api가 완성 되면 db애서 진료과 데이터를 가져와서 하는 것도 좋을 것 같습니다
            + "'마취통증의학과','산부인과','소아청소년과','안과','이비인후과"
            + "','피부과','비뇨의학과','영상의학과','방사선종양학과','병리과"
            + "','진단검사의학과','결핵과','재활의학과','핵의학과','가정의학"
            + "과','응급의학과','직업환경의학과','예방의학과','치과','구강악"
            + "안면외과','치과보철과','치과교정과','소아치과','치주과','치과"
            + "보존과','구강내과','영상치의학과','구강병리과','예방치과','통"
            + "합치의학과','한방내과','한방부인과','한방소아과','한방안·이비"
            + "인후·피부과','한방신경정신과','침구과','한방재활의학과','사상"
            + "체질과','한방응급’]에서 최대 3개를 포함해서 부탁해. 선택된 3개의 "
            + "진료과 단어는 오직 느낌표쌍(!!)만을 앞뒤로 감싸서 표시해줘 (ex. !!내과!!)";

        contents = new Content(new Part(prompt));
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
