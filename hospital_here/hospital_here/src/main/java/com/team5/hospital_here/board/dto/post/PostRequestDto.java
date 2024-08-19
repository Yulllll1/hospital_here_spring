package com.team5.hospital_here.board.dto.post;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.user.entity.user.User;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostRequestDto {
    private Long boardId;
    private Long userId;
    private String userImg;
    private String title;
    private String content;
    private List<String> imageUrls;

    public Post toEntity(Board board, User user) {
        return Post.builder()
                .board(board)
                .user(user)
                .title(this.title)
                .content(this.content)
                .build();
    }
}