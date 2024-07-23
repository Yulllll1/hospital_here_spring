package com.team5.hospital_here.chatMessage.dto;

import lombok.Data;

@Data
public class ChatMessageModifyRequestDTO {
    private Long userId;
    private String content;
}
