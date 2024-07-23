package com.team5.hospital_here.chatMessage.dto;

import lombok.Data;

@Data
public class ChatMessageRequestDTO {
    private Long userId;
    private Long roomId;
    private String content;
}
