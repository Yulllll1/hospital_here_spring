package com.team5.hospital_here.chatMessage.repository;

import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findByChatRoomIdOrderByCreatedAt(Long chatRoomId);

    @Modifying
    @Query("DELETE FROM ChatMessage cm WHERE cm.chatRoom.id = :chatRoomId")
    void deleteByChatRoomId(Long chatRoomId);

}
