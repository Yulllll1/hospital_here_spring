package com.team5.hospital_here.chatMessage.entity;

import com.team5.hospital_here.common.baseEntity.BaseEntity;
import com.team5.hospital_here.chatRoom.entity.ChatRoom;
import com.team5.hospital_here.user.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class ChatMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chat_room_id")
    private ChatRoom chatRoom;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "chatMessage",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<ChatMessageStatus> chatMessageStatuses = new ArrayList<>();

    public void updateChatRoom(ChatRoom chatRoom) {
        this.chatRoom = chatRoom;
        this.chatRoom.getChatMessages().add(this);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
