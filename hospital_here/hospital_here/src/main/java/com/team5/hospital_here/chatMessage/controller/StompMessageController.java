package com.team5.hospital_here.chatMessage.controller;

import com.team5.hospital_here.chatMessage.dto.ChatMessageRequestDTO;
import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompMessageController {

    private final SimpMessageSendingOperations operations;
    private final ChatMessageService chatMessageService;

    @MessageMapping("/chat/{chatRoomId}")
    public ResponseEntity<Void> messageHandler(@DestinationVariable Long chatRoomId, @RequestBody
    ChatMessageRequestDTO chatMessageRequestDTO) {
        ChatMessageResponseDTO chatMessage = chatMessageService.addChatMessage(chatRoomId,
            chatMessageRequestDTO);
        operations.convertAndSend("/queue/" + chatRoomId, chatMessage);

        return ResponseEntity.ok().build();
    }
}
