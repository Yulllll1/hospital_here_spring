package com.team5.hospital_here.chatRoom.dto;

import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.user.entity.user.UserDTO;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileResponseDTO;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomResponseDTO {
    private Long id;
    private UserDTO user1;
    private UserDTO user2;
    private UserDTO leaveUser;
    private ChatRoomType type;
    private ChatRoomStatus status;
    private ChatMessageResponseDTO lastMessage;
    private String createdAt;
    private String updatedAt;
    private DoctorProfileResponseDTO doctorProfile;
}
