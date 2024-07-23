package com.team5.hospital_here.chatMessage.mapper;

import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import com.team5.hospital_here.user.entity.User;
import com.team5.hospital_here.user.entity.UserDTO;
import com.team5.hospital_here.user.entity.UserMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMessageMapper {

    ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

    @Mapping(source = "user", target = "user", qualifiedByName = "userToDTO")
    @Mapping(source = "chatRoom.id", target = "roomId")
    ChatMessageResponseDTO toDTO(ChatMessage chatMessage);

    @Named("userToDTO")
    public static UserDTO userToDTO(User user) {
        return UserMapper.toUserDTO(user);
    }
}
