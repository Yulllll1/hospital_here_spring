package com.team5.hospital_here.chatMessage.mapper;

import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMessageMapper {

    ChatMessageMapper INSTANCE = Mappers.getMapper(ChatMessageMapper.class);

    @Mapping(source = "user", target = "user", qualifiedByName = "userToDTO")
    @Mapping(source = "chatRoom.id", target = "chatRoomId")
    ChatMessageResponseDTO toDTO(ChatMessage chatMessage);

    @Named("userToDTO")
    @Mapping(source = "id", target = "id")
    @Mapping(source = "login.email", target = "email")
    @Mapping(source = "login.password", target = "password")
    @Mapping(source = "login.provider", target = "provider")
    @Mapping(source = "login.providerId", target = "providerId")
    @Mapping(source = "img", target = "image")
    UserDTO userToDTO(User user);
}
