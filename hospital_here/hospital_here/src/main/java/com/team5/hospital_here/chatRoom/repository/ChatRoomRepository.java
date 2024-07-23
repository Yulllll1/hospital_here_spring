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
        + " WHERE (cr.user1 = :id OR cr.user2 = :id)"
        + " AND cr.leaveUser.userId != :id"
        + " ORDER BY cr.createdAt")
    List<ChatRoom> findAllWithUserId(@Param("id") Long id);


}
