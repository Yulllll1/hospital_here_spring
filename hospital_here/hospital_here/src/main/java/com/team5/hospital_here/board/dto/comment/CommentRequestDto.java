package com.team5.hospital_here.board.dto.comment;

import com.team5.hospital_here.board.domain.Comment;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private Long userId;
    private Long parentId;
    private String content;

    public Comment toEntity(Post post, User user, Comment parent) {
        return Comment.builder()
                .post(post)
                .user(user)
                .content(this.content)
                .parent(parent)
                .build();
    }
}
