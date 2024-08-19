package com.team5.hospital_here.chatMessage.controller;

import com.team5.hospital_here.chatMessage.dto.ChatMessageRequestDTO;
import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatMessage.service.ChatMessageService;
import com.team5.hospital_here.chatMessage.service.ChatMessageStatusService;
import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.chatRoom.service.ChatRoomService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StompMessageController {

    private final SimpMessageSendingOperations operations;
    private final ChatRoomService chatRoomService;
    private final ChatMessageService chatMessageService;
    private final ChatMessageStatusService chatMessageStatusService;

    @MessageMapping("/chat/{chatRoomId}")
    public ResponseEntity<Void> messageHandler(@DestinationVariable Long chatRoomId, @RequestBody
    ChatMessageRequestDTO chatMessageRequestDTO) {

        ChatMessageResponseDTO chatMessage = ChatMessageResponseDTO.builder()
            .id(chatMessageRequestDTO.getId()).userId(chatMessageRequestDTO.getUserId())
            .isAccepted(false).isTerminated(false).build();

        // 채팅 수락 요청
        if (chatMessageRequestDTO.getIsAccepted()) {
            chatMessage.setIsAccepted(true);
            sendChatRoom(chatMessageRequestDTO.getUserId(),
                chatRoomService.findChatRoom(chatRoomId, chatMessageRequestDTO.getUserId()));
            // 채팅방 종료(나가기)
        } else if (chatMessageRequestDTO.getIsTerminated()) {
            chatMessage.setIsTerminated(true);
            sendChatRoom(chatMessageRequestDTO.getUserId(),
                chatRoomService.findChatRoom(chatRoomId, chatMessageRequestDTO.getUserId()));
        } else {
            // id가 없다면 새로운 메시지
            if (chatMessageRequestDTO.getId() == null) {
                chatMessage = chatMessageService.addChatMessage(chatRoomId,
                    chatMessageRequestDTO);
                chatMessageStatusService.saveStatusToFalse(chatMessageRequestDTO.getUserId(),
                    chatMessage.getId());

                ChatRoomResponseDTO updatedChatRoom = chatRoomService.findChatRoom(chatRoomId,
                    chatMessageRequestDTO.getUserId());

                if (updatedChatRoom.getUser2() != null) {
                    sendChatRoom(chatMessageRequestDTO.getUserId(), updatedChatRoom);
                } else {
                    if (updatedChatRoom.getType() == ChatRoomType.DOCTOR) {
                        operations.convertAndSend("/queue/list/" + ChatRoomType.DOCTOR.name(),
                            updatedChatRoom);
                    } else if (updatedChatRoom.getType() == ChatRoomType.SERVICE) {
                        operations.convertAndSend("/queue/list/ADMIN",
                            updatedChatRoom);
                    }
                }
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
        }

        operations.convertAndSend("/queue/" + chatRoomId, chatMessage);

        return ResponseEntity.ok().build();
    }

    private void sendChatRoom(Long userId, ChatRoomResponseDTO updatedChatRoom) {
        Long targetUserId =
            Objects.equals(updatedChatRoom.getUser1().getId(),
                userId)
                ? updatedChatRoom.getUser2().getId()
                : updatedChatRoom.getUser1().getId();

        operations.convertAndSend("/queue/list/" + targetUserId, updatedChatRoom);
    }
}
