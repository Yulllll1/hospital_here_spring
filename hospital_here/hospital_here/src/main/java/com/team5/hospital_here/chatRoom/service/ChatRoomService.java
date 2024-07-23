package com.team5.hospital_here.chatRoom.service;

import com.team5.hospital_here.chatRoom.dto.ChatRoomRequestDTO;
import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.chatRoom.mapper.ChatRoomMapper;
import com.team5.hospital_here.chatRoom.repository.ChatRoomRepository;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.User;
import com.team5.hospital_here.user.repository.UserRepository;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    // 모든 채팅방 조회
    public List<ChatRoomResponseDTO> findAllChatRoom(Long userId) {
        List<ChatRoom> chatRooms = chatRoomRepository.findAllWithUserId(userId);

        return chatRooms.stream().map(ChatRoomMapper.INSTANCE::toDto).toList();
    }

    // 채팅방 생성
    public ChatRoomResponseDTO saveChatRoom(Long userId, String chatRoomType) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom newChatRoom = ChatRoom.builder()
            .user1(foundUser)
            .type(ChatRoomType.valueOf(chatRoomType))
//            .status(ChatRoomStatus.WAITING)
            .build();

        ChatRoomType type = newChatRoom.getType();
        if (type == ChatRoomType.AI) {
            newChatRoom.updateStatus(ChatRoomStatus.ACTIVE);
        } else {
            newChatRoom.updateStatus(ChatRoomStatus.WAITING);
        }

        return ChatRoomMapper.INSTANCE.toDto(chatRoomRepository.save(newChatRoom));
    }

    public ChatRoomResponseDTO acceptChatRoom(Long userId, Long chatRoomId) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        if (foundChatRoom.isChatRoomMember(foundUser.getUserId())) {
            throw new CustomException(ErrorCode.CHAT_ROOM_ALREADY_BELONG);
        }

        ChatRoomStatus status = foundChatRoom.getStatus();
        if (status == ChatRoomStatus.WAITING) {
            foundChatRoom.updateStatus(ChatRoomStatus.ACTIVE);
        }

        foundChatRoom.addChatRoomMember(foundUser);

        ChatRoom updatedChatRoom = chatRoomRepository.save(foundChatRoom);

        return ChatRoomMapper.INSTANCE.toDto(updatedChatRoom);
    }

    public ChatRoomResponseDTO leaveChatRoom(Long userId, Long chatRoomId) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        if (!foundChatRoom.isChatRoomMember(foundUser.getUserId())) {
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_BELONG);
        }

        ChatRoomStatus status = foundChatRoom.getStatus();
        if (status == ChatRoomStatus.ACTIVE) { // 채팅이 진행 중인 경우
            foundChatRoom.addLeaveUser(foundUser);
            foundChatRoom.updateStatus(ChatRoomStatus.INACTIVE); // 채팅 비활성화 (상대가 나간 상태)
        } else if (status == ChatRoomStatus.INACTIVE
            && foundChatRoom.getLeaveUser() != null) { // 채팅이 비활성화인 경우
            chatRoomRepository.delete(foundChatRoom);
            return null;
        }

        ChatRoom updatedChatRoom = chatRoomRepository.save(foundChatRoom);

        return ChatRoomMapper.INSTANCE.toDto(updatedChatRoom);
    }

    // 채팅방 수정
    public ChatRoomResponseDTO modifyChatRoom(ChatRoomRequestDTO chatRoomRequestDTO) {
        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomRequestDTO.getId())
            .orElseThrow(() ->
                new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        Long requestUserId = chatRoomRequestDTO.getUserId();
        if (requestUserId != null) {
            if (foundChatRoom.isChatRoomMember(requestUserId)) { // 이미 채팅방에 속한 유저라면
                throw new CustomException(ErrorCode.CHAT_ROOM_ACCESS_FAILED);
            }

            User foundUser = userRepository.findById(requestUserId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

            foundChatRoom.addChatRoomMember(foundUser); // 채팅방에 유저 추가
            foundChatRoom.updateStatus(ChatRoomStatus.ACTIVE); // 채팅방 활성화(ACTIVE)
        }

        ChatRoom updatedChatRoom = chatRoomRepository.save(foundChatRoom);

        return ChatRoomMapper.INSTANCE.toDto(updatedChatRoom);
    }
}
