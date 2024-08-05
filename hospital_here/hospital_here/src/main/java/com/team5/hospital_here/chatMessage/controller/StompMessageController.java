package com.team5.hospital_here.chatMessage.controller;

import com.team5.hospital_here.chatMessage.dto.ChatMessageRequestDTO;
import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatMessage.repository.ChatMessageRepository;
import com.team5.hospital_here.chatMessage.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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

        ChatMessageResponseDTO chatMessage = ChatMessageResponseDTO.builder()
            .id(chatMessageRequestDTO.getId()).build();

        // id가 없다면 새로운 메시지
        if (chatMessageRequestDTO.getId() == null) {
            chatMessage = chatMessageService.addChatMessage(chatRoomId,
                chatMessageRequestDTO);
        } else {
            // content가 null이면 메시지 삭제
            if (chatMessageRequestDTO.getContent() == null) {
                chatMessageService.removeChatMessage(chatMessageRequestDTO.getId());
                chatMessage.setContent(null);
            } else {
                // 메시지 수정
                chatMessage = chatMessageService.modifyChatMessage(
                    chatMessageRequestDTO.getId(),
                    chatMessageRequestDTO.getUserId(), chatMessageRequestDTO.getContent());
            }
        }

        operations.convertAndSend("/queue/" + chatRoomId, chatMessage);

        return ResponseEntity.ok().build();
    }
}
