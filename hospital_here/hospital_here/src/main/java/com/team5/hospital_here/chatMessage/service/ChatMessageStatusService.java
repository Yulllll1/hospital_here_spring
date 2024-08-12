package com.team5.hospital_here.chatMessage.service;

import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import com.team5.hospital_here.chatMessage.entity.ChatMessageStatus;
import com.team5.hospital_here.chatMessage.repository.ChatMessageRepository;
import com.team5.hospital_here.chatMessage.repository.ChatMessageStatusRepository;
import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageStatusService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageStatusRepository chatMessageStatusRepository;
    private final UserRepository userRepository;

    // 읽지 않음 상태의 메시지 수 요청
    public Integer getCountNotRead(Long userId) {
        return chatMessageStatusRepository.countByUserIdAndIsRead(userId, false);
    }

    // 읽음 상태로 업데이트
    public void updateStatusToRead(Long userId, Long chatMessageId) {
        ChatMessage foundChatMessage = chatMessageRepository.findById(chatMessageId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));

        // 채팅방이 진행 중이지 않다면 무시
        if (foundChatMessage.getChatRoom().getStatus() != ChatRoomStatus.ACTIVE) {
            return;
        }

        userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        chatMessageStatusRepository.updateIsReadWithinTime(true, userId, LocalDateTime.now());
    }

    // 새로 저장된 메시지에 대한 상대방의 읽음 상태를 false로 우선 저장
    public void saveStatusToFalse(Long userId, Long chatMessageId) {
        ChatMessage foundChatMessage = chatMessageRepository.findById(chatMessageId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_MESSAGE_NOT_FOUND));

        ChatRoom foundChatRoom = foundChatMessage.getChatRoom();

        // 채팅방이 진행 중이지 않다면 무시
        if (foundChatRoom.getStatus() != ChatRoomStatus.ACTIVE) {
            return;
        }

        userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 채팅 상대방의 user
        User targetUser =
            Objects.equals(foundChatRoom.getUser1().getId(), userId) ? foundChatRoom.getUser2()
                : foundChatRoom.getUser1();

        // 우선 읽지 않은 상태로 저장
        ChatMessageStatus newChatMessageStatus = ChatMessageStatus.builder()
            .chatMessage(foundChatMessage).isRead(false).user(targetUser).build();

        chatMessageStatusRepository.save(newChatMessageStatus);
    }
}
