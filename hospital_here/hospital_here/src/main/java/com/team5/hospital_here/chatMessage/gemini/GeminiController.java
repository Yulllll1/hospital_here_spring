package com.team5.hospital_here.chatMessage.gemini;

import com.team5.hospital_here.chatMessage.gemini.dto.GeminiRequestDTO;
import com.team5.hospital_here.chatMessage.gemini.dto.RequestDTO;
import com.team5.hospital_here.chatMessage.gemini.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gemini")
public class GeminiController {

    private final GeminiService geminiService;

    @PostMapping
    public ResponseDTO prompt(@RequestBody RequestDTO requestDTO) {
        return geminiService.prompt(requestDTO.getMessage());
    }
}
