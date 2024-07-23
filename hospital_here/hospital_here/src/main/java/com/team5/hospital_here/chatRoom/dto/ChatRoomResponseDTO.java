package com.team5.hospital_here.chatRoom.dto;

import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.user.entity.UserDTO;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomResponseDTO {
    private Long id;
    private UserDTO user1;
    private UserDTO user2;
    private ChatRoomType type;
    private ChatRoomStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
