package com.team5.hospital_here.chatRoom.controller;

import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.service.ChatRoomService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/chatrooms")
public class ChatRoomAdminController {

    private final ChatRoomService chatRoomService;

    // 채팅방 전체 목록
    @GetMapping
    public ResponseEntity<List<ChatRoomResponseDTO>> chatRoomList() {
        List<ChatRoomResponseDTO> chatRoomList = chatRoomService.findAll();

        return ResponseEntity.ok().body(chatRoomList);
    }

    // 채팅방 삭제 (강제?)
    @DeleteMapping("/{chatRoomId}")
    public ResponseEntity<Void> chatRoomRemove(@PathVariable Long chatRoomId) {
        chatRoomService.removeChatRoom(chatRoomId);

        return ResponseEntity.noContent().build();
    }
}
