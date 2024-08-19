package com.team5.hospital_here.chatMessage.repository;

import com.team5.hospital_here.chatMessage.entity.ChatMessageStatus;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageStatusRepository extends JpaRepository<ChatMessageStatus, Long> {

    @Query("SELECT COUNT(*) FROM ChatMessageStatus cms "
        + "WHERE cms.isRead = :isRead "
        + "AND cms.user.id = :userId "
        + "AND cms.chatMessage.chatRoom.id = :chatRoomId")
    Integer countByUserIdAndIsRead(Long chatRoomId, Boolean isRead, Long userId);

    @Modifying
    @Query("UPDATE ChatMessageStatus cms SET cms.isRead = :isRead "
        + "WHERE cms.user.id = :userId "
        + "AND cms.chatMessage.chatRoom.id = :chatRoomId "
        + "AND cms.createdAt <= :createdAt")
    void updateIsReadWithinTime(Boolean isRead, Long chatRoomId, Long userId,
        LocalDateTime createdAt);

    @Modifying
    @Query("DELETE FROM ChatMessageStatus cms WHERE cms.chatMessage.chatRoom.id = :chatRoomId")
    void deleteAll(Long chatRoomId);
}
