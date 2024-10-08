package com.team5.hospital_here.chatRoom.service;

import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import com.team5.hospital_here.chatMessage.mapper.ChatMessageMapper;
import com.team5.hospital_here.chatMessage.repository.ChatMessageRepository;
import com.team5.hospital_here.chatMessage.repository.ChatMessageStatusRepository;
import com.team5.hospital_here.chatMessage.service.ChatMessageStatusService;
import com.team5.hospital_here.chatRoom.dto.ChatRoomResponseDTO;
import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.chatRoom.mapper.ChatRoomMapper;
import com.team5.hospital_here.chatRoom.repository.ChatRoomRepository;
import com.team5.hospital_here.common.exception.CustomException;
import com.team5.hospital_here.common.exception.ErrorCode;
import com.team5.hospital_here.common.jwt.CustomUser;
import com.team5.hospital_here.user.entity.Role;
import com.team5.hospital_here.user.entity.user.User;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfile;
import com.team5.hospital_here.user.entity.user.doctorEntity.DoctorProfileResponseDTO;
import com.team5.hospital_here.user.repository.DoctorProfileRepository;
import com.team5.hospital_here.user.repository.UserRepository;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final ChatMessageStatusRepository chatMessageStatusRepository;
    private final ChatMessageStatusService chatMessageStatusService;
    private final UserRepository userRepository;
    private final DoctorProfileRepository doctorProfileRepository;

    public List<ChatRoomResponseDTO> findAll() {
        List<ChatRoom> list = chatRoomRepository.findAll();

        if (list.isEmpty()) {
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_EXIST);
        }

        List<ChatRoomResponseDTO> chatRoomList = list.stream()
            .map(ChatRoomMapper.INSTANCE::toDto)
            .toList();

        setLastMessageAndDoctorProfile(chatRoomList, list);

        return chatRoomList;
    }

    public ChatRoomResponseDTO findChatRoom(Long chatRoomId, Long userId) {
        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
            new CustomException(ErrorCode.CHAT_ROOM_NOT_EXIST));

        ChatRoomResponseDTO response = ChatRoomMapper.INSTANCE.toDto(foundChatRoom);

        if (foundChatRoom.getStatus() == ChatRoomStatus.ACTIVE) {
            Long targetUserId =
                Objects.equals(foundChatRoom.getUser1().getId(), userId) ? foundChatRoom.getUser2()
                    .getId() :
                    foundChatRoom.getUser1().getId();

            response.setNewMessageCount(
                chatMessageStatusService.getCountNotRead(chatRoomId, targetUserId));
        }

        setLastMessageAndDoctorProfile(List.of(response), List.of(foundChatRoom));

        return response;
    }

    // 모든 채팅방 조회
    public List<ChatRoomResponseDTO> findAllChatRoom(Long userId) throws CustomException {
        userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<ChatRoom> foundChatRoomList = chatRoomRepository.findAllWithUserId(userId);
        if (foundChatRoomList.isEmpty()) {
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_EXIST);
        }

        List<ChatRoomResponseDTO> chatRoomResponseList = foundChatRoomList.stream()
            .map(ChatRoomMapper.INSTANCE::toDto)
            .peek(e -> e.setNewMessageCount(
                chatMessageStatusService.getCountNotRead(e.getId(), userId)))
            .toList();

        setLastMessageAndDoctorProfile(chatRoomResponseList, foundChatRoomList);

        return chatRoomResponseList;
    }

    // 수락 대기 중인 채팅방 조회
    public List<ChatRoomResponseDTO> findAllWaitingChatRoom(Long userId) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        // 관리자도 의사도 아닌 경우
        if (foundUser.getRole() != Role.ADMIN && foundUser.getRole() != Role.DOCTOR) {
            throw new CustomException(ErrorCode.NO_PERMISSION);
        }

        Role userRole = foundUser.getRole();
        List<ChatRoom> foundChatRoomList = chatRoomRepository.findByStatus(ChatRoomStatus.WAITING)
            .stream().filter(
                e -> e.getType() == (userRole == Role.ADMIN ? ChatRoomType.SERVICE
                    : ChatRoomType.DOCTOR)
            ).toList();

        if (foundChatRoomList.isEmpty()) {
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_EXIST);
        }

        List<ChatRoomResponseDTO> chatRoomResponseList = foundChatRoomList.stream()
            .map(ChatRoomMapper.INSTANCE::toDto).toList();

        setLastMessageAndDoctorProfile(chatRoomResponseList, foundChatRoomList);

        return chatRoomResponseList;
    }

    // ChatRoomResponseDTO의 lastMessage, doctorProfile 채우기
    private void setLastMessageAndDoctorProfile(List<ChatRoomResponseDTO> list,
        List<ChatRoom> chatRooms) {
        for (int i = 0; i < list.size(); i++) {
            ChatRoomResponseDTO chatRoomElement = list.get(i);
            try {
                List<ChatMessage> chatRoomsMessages = chatRooms.get(i).getChatMessages();
                chatRoomElement.setLastMessage(ChatMessageMapper.INSTANCE.toDTO(
                    chatRoomsMessages.get(chatRoomsMessages.size() - 1)));
            } catch (IndexOutOfBoundsException e) {
                chatRoomElement.setLastMessage(null);
            }

            if (chatRoomElement.getType() == ChatRoomType.DOCTOR) {
                if (chatRoomElement.getStatus() != ChatRoomStatus.WAITING) {
                    doctorProfileRepository.findByUser(
                            chatRooms.get(i).getUser2())
                        .ifPresent(foundDoctorProfile -> chatRoomElement.setDoctorProfile(
                            new DoctorProfileResponseDTO(foundDoctorProfile)));
                }
            }
        }
    }

    // 채팅방 생성
    public ChatRoomResponseDTO saveChatRoom(CustomUser customUser, ChatRoomType chatRoomType) {
        User foundUser = customUser.getUser();

        ChatRoom newChatRoom = ChatRoom.builder()
            .user1(foundUser)
            .type(chatRoomType)
            .build();

        ChatRoomType type = newChatRoom.getType();
        if (type == ChatRoomType.AI) {
            newChatRoom.updateStatus(ChatRoomStatus.ACTIVE);
        } else {
            newChatRoom.updateStatus(ChatRoomStatus.WAITING);
        }
        ChatRoom updatedChatRoom = chatRoomRepository.save(newChatRoom);

        return ChatRoomMapper.INSTANCE.toDto(updatedChatRoom);
    }

    public ChatRoomResponseDTO acceptChatRoom(Long userId, Long chatRoomId) {
        User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId)
            .orElseThrow(() -> new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        if (foundChatRoom.getUser2() != null) {
            throw new CustomException(ErrorCode.CHAT_ROOM_ACCESS_FAILED);
        }

        if (foundChatRoom.isChatRoomMember(foundUser.getId())) {
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

        if (!foundChatRoom.isChatRoomMember(foundUser.getId())) {
            throw new CustomException(ErrorCode.CHAT_ROOM_NOT_BELONG);
        }

        ChatRoomStatus status = foundChatRoom.getStatus();
        if (status != ChatRoomStatus.ACTIVE) {
            chatMessageStatusRepository.deleteAll(chatRoomId);
            chatMessageRepository.deleteByChatRoomId(chatRoomId);
            chatRoomRepository.deleteById(foundChatRoom.getId());
            return null;
        }

        foundChatRoom.addLeaveUser(foundUser);
        foundChatRoom.updateStatus(ChatRoomStatus.INACTIVE);

        ChatRoom updatedChatRoom = chatRoomRepository.save(foundChatRoom);

        return ChatRoomMapper.INSTANCE.toDto(updatedChatRoom);
    }

    public void removeChatRoom(Long chatRoomId) {
        ChatRoom foundChatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
            new CustomException(ErrorCode.CHAT_ROOM_NOT_FOUND));

        chatMessageRepository.deleteByChatRoomId(chatRoomId);
        chatRoomRepository.delete(foundChatRoom);
    }
}
