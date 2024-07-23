package com.team5.hospital_here.board.dto.comment;

import com.team5.hospital_here.board.domain.Board;
import com.team5.hospital_here.board.domain.Comment;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private Long userId;
    private String parentId;
    private String content;

}
