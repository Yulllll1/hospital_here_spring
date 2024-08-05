package com.team5.hospital_here.chatMessage.service;

import com.team5.hospital_here.chatMessage.dto.ChatMessageRequestDTO;
import com.team5.hospital_here.chatMessage.dto.ChatMessageResponseDTO;
import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import com.team5.hospital_here.chatMessage.mapper.ChatMessageMapper;
import com.team5.hospital_here.chatMessage.repository.ChatMessageRepository;
import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.chatRoom.repository.ChatRoomRepository;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileResponseDTO;
import com.team5.hospital_here.user.repository.DoctorProfileRepository;
import com.team5.hospital_here.user.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    public List<ChatMessageResponseDTO> findAllChatMessage(Long chatRoomId, Long userId) {
        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        if (foundChatRoom.getStatus() == ChatRoomStatus.ACTIVE && !foundChatRoom.isChatRoomMember(
            userId)) {
            throw new CustomException(ErrorCode.CHAT_ROOM_ACCESS_FAILED);
        }

        List<ChatMessage> chatMessageList = chatMessageRepository.findByChatRoomIdOrderByCreatedAt(
            chatRoomId);

        List<ChatMessageResponseDTO> list = chatMessageList.stream()
            .map(ChatMessageMapper.INSTANCE::toDTO).toList();

        setDoctorProfile(list);

        return list;
    }

    // ChatMessageResponseDTO의 doctorProfile 채우기
    private void setDoctorProfile(List<ChatMessageResponseDTO> list) {
        for (int i = 0; i < list.size(); i++) {
            ChatMessageResponseDTO chatMessageElement = list.get(i);

            // 해당 메시지가 의사의 메시지인 경우
            if (chatMessageElement.getUser().getRole().equals(Role.DOCTOR.name())) {
                DoctorProfile foundDoctorProfile = doctorProfileRepository.findByUser(
                        userRepository.findById(chatMessageElement.getUser().getId())
                            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND)))
                    .orElseThrow(() -> new CustomException(ErrorCode.DOCTOR_PROFILE_NOT_FOUND));

                chatMessageElement.setDoctorProfile(
                    new DoctorProfileResponseDTO(foundDoctorProfile));
            }
        }
    }

    public ChatMessageResponseDTO addChatMessage(Long chatRoomId,
        ChatMessageRequestDTO chatMessageRequestDTO) {
        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        User foundUser = userRepository.findById(chatMessageRequestDTO.getUserId()).orElseThrow(() ->
            new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatMessage newChatMessage = ChatMessage.builder()
            .content(chatMessageRequestDTO.getContent())
            .user(foundUser).build();
        newChatMessage.updateChatRoom(foundChatRoom);

        ChatMessage createdChatMessage = chatMessageRepository.save(newChatMessage);

        return ChatMessageMapper.INSTANCE.toDTO(createdChatMessage);
    }

    public ChatMessageResponseDTO modifyChatMessage(Long chatMessageId, Long userId, String content) {
        if (content == null || content.isEmpty()) {
            throw new CustomException(ErrorCode.CHAT_MESSAGE_NOT_CONTENT);
        }

        userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatMessage foundChatMessage = chatMessageRepository.findById(chatMessageId).orElseThrow(() ->
            new CustomException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));
        foundChatMessage.updateContent(content);

        ChatMessage updatedChatMessage = chatMessageRepository.save(foundChatMessage);

        return ChatMessageMapper.INSTANCE.toDTO(updatedChatMessage);
    }

    public void removeChatMessage(Long chatMessageId) {
        chatMessageRepository.deleteById(chatMessageId);
    }
}
