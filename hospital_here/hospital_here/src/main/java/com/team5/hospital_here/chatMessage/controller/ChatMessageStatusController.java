package com.team5.hospital_here.chatMessage.controller;

import com.team5.hospital_here.chatMessage.dto.ChatMessageRequestDTO;
import com.team5.hospital_here.chatMessage.service.ChatMessageStatusService;
import com.team5.hospital_here.common.jwt.CustomUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat-messages/status")
@RequiredArgsConstructor
public class ChatMessageStatusController {

    private final ChatMessageStatusService chatMessageStatusService;

    @PostMapping
    public ResponseEntity<Void> chatMessageRead(@AuthenticationPrincipal CustomUser customUser,
        @RequestBody ChatMessageRequestDTO chatMessageRequestDTO) {
        chatMessageStatusService.updateStatusToRead(customUser.getUser().getId(),
            chatMessageRequestDTO.getId());

        return ResponseEntity.ok().build();
    }
}
