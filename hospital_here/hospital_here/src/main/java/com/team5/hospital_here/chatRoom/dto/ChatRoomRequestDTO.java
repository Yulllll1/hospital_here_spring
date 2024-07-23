package com.team5.hospital_here.chatRoom.dto;

import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomRequestDTO {
    private Long id;
    private Long userId;
    private ChatRoomType chatRoomType;
    private String type;
    private String status;
}
