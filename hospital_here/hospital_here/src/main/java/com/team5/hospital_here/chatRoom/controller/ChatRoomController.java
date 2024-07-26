package com.team5.hospital_here.chatRoom.controller;

import com.team5.hospital_here.chatRoom.dto.ChatRoomRequestDTO;
import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatrooms")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 채팅방 목록 요청
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> chatRoomList(@RequestParam Long userId) {
        List<ChatRoomResponseDTO> chatRoomList = chatRoomService.findAllChatRoom(userId);

        return ResponseEntity.ok().body(chatRoomList);
    }

    @GetMapping("/wait")
    public ResponseEntity<List<ChatRoomResponseDTO>> waitingChatRoomList(
        @RequestParam Long userId) {
        List<ChatRoomResponseDTO> waitingChatRoomList = chatRoomService.findAllWaitingChatRoom(
            userId);

        return ResponseEntity.ok().body(waitingChatRoomList);
    }

    // 채팅방 생성
    @PostMapping
    public ResponseEntity<ChatRoomResponseDTO> chatRoomAdd(@RequestBody ChatRoomRequestDTO chatRoomRequestDTO) {
        ChatRoomResponseDTO newChatRoom = chatRoomService.saveChatRoom(
            chatRoomRequestDTO.getUserId(),
            chatRoomRequestDTO.getChatRoomType());

        return ResponseEntity.status(HttpStatus.CREATED).body(newChatRoom);
    }

    // 채팅 요청 수락
    @PostMapping("/{chatRoomId}/users/{userId}")
    public ResponseEntity<ChatRoomResponseDTO> chatRoomAccept(@PathVariable Long chatRoomId,
        @PathVariable Long userId) {
        ChatRoomResponseDTO updatedChatRoom = chatRoomService.acceptChatRoom(
            userId, chatRoomId);

        return ResponseEntity.ok().body(updatedChatRoom);
    }

    // 채팅방 나가기(삭제)
    @DeleteMapping("/{chatRoomId}/users/{userId}")
    public ResponseEntity<ChatRoomResponseDTO> chatRoomLeave(@PathVariable Long chatRoomId,
        @PathVariable Long userId) {
        ChatRoomResponseDTO updatedChatRoom = chatRoomService.leaveChatRoom(userId, chatRoomId);

        if (updatedChatRoom == null) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.ok().body(updatedChatRoom);
    }
}
