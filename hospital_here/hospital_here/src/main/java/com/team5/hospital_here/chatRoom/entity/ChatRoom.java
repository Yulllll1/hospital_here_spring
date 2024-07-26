package com.team5.hospital_here.chatRoom.entity;

import com.team5.hospital_here.chatMessage.entity.ChatMessage;
import com.team5.hospital_here.chatRoom.enums.ChatRoomStatus;
import com.team5.hospital_here.chatRoom.enums.ChatRoomType;
import com.team5.hospital_here.common.baseEntity.BaseEntity;
import com.team5.hospital_here.user.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ChatRoom extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user1_id")
    private User user1;

    @ManyToOne
    @JoinColumn(name = "user2_id")
    private User user2;

    @Enumerated(EnumType.STRING)
    private ChatRoomType type;

    @Enumerated(EnumType.STRING)
    private ChatRoomStatus status;

    @ManyToOne
    @JoinColumn(name = "leave_user_id")
    private User leaveUser;

    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.PERSIST, orphanRemoval = true)
    @OrderBy("createdAt")
    private List<ChatMessage> chatMessages = new ArrayList<>();

    public void addLeaveUser(User user) {
        leaveUser = user;
    }

    public void updateStatus(ChatRoomStatus chatRoomStatus) {
        status = chatRoomStatus;
    }

    public boolean isChatRoomMember(Long userId) {
        if (leaveUser != null) {
            if (leaveUser.getId().equals(userId)) {
                return false;
            }
        }

        if (user1 != null) {
            if (user1.getId().equals(userId)) {
                return true;
            }
        }

        if (user2 != null) {
            if (user2.getId().equals(userId)) {
                return true;
            }
        }

        return false;
    }

    public void addChatRoomMember(User user) {
        if (user1 == null) {
            user1 = user;
            return;
        }

        user2 = user;
    }
}
