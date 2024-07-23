package com.team5.hospital_here.chatRoom.repository;

import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    @Query("SELECT cr FROM ChatRoom cr JOIN FETCH cr.chatMessages"
        + " JOIN FETCH cr.user1"
        + " JOIN FETCH cr.user2"
        + " WHERE (cr.user1.id = :id OR cr.user2.id = :id)"
        + " AND cr.leaveUser.id != :id"
        + " ORDER BY cr.createdAt")
    List<ChatRoom> findAllWithUserId(@Param("id") Long id);
}
