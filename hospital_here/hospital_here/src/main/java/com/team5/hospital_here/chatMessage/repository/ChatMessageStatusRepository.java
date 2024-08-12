package com.team5.hospital_here.chatMessage.repository;

import com.team5.hospital_here.chatMessage.entity.ChatMessageStatus;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageStatusRepository extends JpaRepository<ChatMessageStatus, Long> {

    Integer countByUserIdAndIsRead(Long chatRoomId, Boolean isRead);

    @Modifying
    @Query("UPDATE ChatMessageStatus cms SET cms.isRead = :isRead "
        + "WHERE cms.user.id = :userId "
        + "AND cms.createdAt <= :createdAt")
    void updateIsReadWithinTime(Boolean isRead,
        Long userId,
        LocalDateTime createdAt);
}
