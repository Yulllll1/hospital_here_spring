package com.team5.hospital_here.chatMessage.gemini;

import com.team5.hospital_here.chatMessage.gemini.dto.GeminiRequestDTO;
import com.team5.hospital_here.chatMessage.gemini.dto.GeminiResponseDTO;
import com.team5.hospital_here.chatMessage.gemini.dto.ResponseDTO;
import com.team5.hospital_here.hospital.entity.Symptom;
import com.team5.hospital_here.hospital.service.SymptomService;
import com.team5.hospital_here.hospital.service.SymptomDepartmentService;
import com.team5.hospital_here.hospital.entity.Department;
import com.team5.hospital_here.hospital.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeminiService {

    @Value("${GEMINI.API.URL}")
    private String url;

    @Value("${GEMINI.API.KEY}")
    private String key;

    private final RestTemplate restTemplate;
    private final DepartmentRepository departmentRepository;
    private final SymptomService symptomService;
    private final SymptomDepartmentService symptomDepartmentService;

    public ResponseDTO prompt(String userMessage) {
        // 1. 프롬프트 생성
        String prompt = generatePrompt(userMessage);

        // GeminiRequestDTO 생성
        GeminiRequestDTO.Part part = new GeminiRequestDTO.Part(prompt);
        GeminiRequestDTO.Content content = new GeminiRequestDTO.Content(part);
        GeminiRequestDTO geminiRequestDTO = new GeminiRequestDTO(content);

        // 2. GEMINI API 요청
        GeminiResponseDTO response = callGeminiApi(geminiRequestDTO);

        // 3. 사용자의 증상 저장
        Symptom savedSymptom = symptomService.saveSymptom(userMessage);

        // 4. GEMINI 응답에서 진료과 추출
        ResponseDTO responseDTO = parseResponse(response);

        // 5. 증상과 진료과 간의 연관 저장
        symptomDepartmentService.saveSymptomDepartments(savedSymptom, responseDTO.getDepartments());

        return responseDTO;
    }

    private String generatePrompt(String message) {
        // DB에서 진료과 목록 가져오기
        List<Department> departments = departmentRepository.findAll();
        String departmentList = departments.stream()
            .map(Department::getName)
            .map(name -> "'" + name + "'")
            .collect(Collectors.joining(","));

        // 프롬프트 문자열 생성
        return "'" + message + "'에 대한 소견을 [" + departmentList + "]에서 최대 3개를 포함해서 밝고 친절한 말투로 부탁해. 선택된 3개의 진료과 단어는 오직 해시쌍(##)만을 앞뒤로 감싸서 표시해줘 (ex. ##내과##)";
    }

    private GeminiResponseDTO callGeminiApi(GeminiRequestDTO geminiRequestDTO) {
        String requestUrl = url + "?key=" + key;
        GeminiResponseDTO response = restTemplate.postForObject(requestUrl, geminiRequestDTO, GeminiResponseDTO.class);

        if (response == null) {
            throw new RuntimeException("Gemini 응답 데이터 없음");
        }

        return response;
    }

    private ResponseDTO parseResponse(GeminiResponseDTO geminiResponseDTO) {
        String resultMessage = geminiResponseDTO.getCandidates().get(0).getContent().getParts()
            .get(0).getText();

        Set<String> departments = new HashSet<>();
        parseDepartments(resultMessage, departments);

        return ResponseDTO.builder()
            .message(resultMessage)
            .departments(departments)
            .build();
    }

    private void parseDepartments(String resultMessage, Set<String> departments) {
        for (int i = 0; i < resultMessage.length(); i++) {
            char c = resultMessage.charAt(i);
            if (c == '#') {
                i += 2;

                StringBuilder token = new StringBuilder();
                for (int j = i; j < resultMessage.length(); j++) {
                    if (resultMessage.charAt(j) == '#') {
                        departments.add(token.toString());
                        i = j + 2;
                        break;
                    }
                    token.append(resultMessage.charAt(j));
                }
            }
        }
    }
}
