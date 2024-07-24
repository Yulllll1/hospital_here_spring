package com.team5.hospital_here.chatRoom.mapper;

import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.user.entity.UserMapper;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatRoomMapper {

    ChatRoomMapper INSTANCE = Mappers.getMapper(ChatRoomMapper.class);

    @Mapping(source = "user1", target = "user1", qualifiedByName = "userToDTO")
    @Mapping(source = "user2", target = "user2", qualifiedByName = "userToDTO")
    @Mapping(source = "leaveUser", target = "leaveUser", qualifiedByName = "userToDTO")
    ChatRoomResponseDTO toDto(ChatRoom chatRoom);

    @Named("userToDTO")
    @Mapping(source = "id", target = "id")
    UserDTO userToDTO(User user);
}
