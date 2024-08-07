package com.team5.hospital_here.board.domain;
import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.comment.CommentUpdateDto;
import com.team5.hospital_here.common.baseEntity.BaseEntity;
import com.team5.hospital_here.user.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty(message = "필수 입력값 입니다.")
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Builder.Default
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> replies = new ArrayList<>();

    public void update(CommentUpdateDto commentUpdateDto) {
        this.content = commentUpdateDto.getContent();
    }

    public CommentResponseDto toResponseDto() {
        return CommentResponseDto.builder()
                .id(this.id)
                .postId(this.post != null ? this.post.getId() : null)
                .userId(this.user != null ? this.user.getId() : null)
                .content(this.content)
                .parentId(this.parent != null ? this.parent.getId() : null)
                .userName(this.user.getName())
                .createdAt(this.getCreatedAt())
                .build();
    }
}

