package com.team5.hospital_here.chatRoom.repository;

import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import java.util.List;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @EntityGraph(attributePaths = {"chatMessages", "user1", "user2", "leaveUser"})
    @Query("SELECT cr FROM ChatRoom cr"
        + " WHERE ( cr.user1.id = :id OR cr.user2.id = :id )"
        + " AND ( cr.leaveUser.id != :id OR cr.leaveUser IS NULL ) "
        + " ORDER BY cr.createdAt")
    List<ChatRoom> findAllWithUserId(Long id);

    List<ChatRoom> findByStatus(ChatRoomStatus status);
}
