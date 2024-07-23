package com.team5.hospital_here.board.dto.post;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.User;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long boardId;
    private Long userId;
    private String title;
    private String content;

    public Post toEntity(Board board, User user) {
        return Post.builder()
                .board(board)
                .user(user)
                .title(this.title)
                .content(this.content)
                .build();
    }
}