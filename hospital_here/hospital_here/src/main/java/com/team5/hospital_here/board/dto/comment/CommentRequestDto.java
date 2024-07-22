package com.team5.hospital_here.board.dto.comment;

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
