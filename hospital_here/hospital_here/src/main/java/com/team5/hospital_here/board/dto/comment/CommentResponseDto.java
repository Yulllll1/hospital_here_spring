package com.team5.hospital_here.board.dto.comment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private Long postId;
    private Long userId;
    private Long parentId;
    private String content;
}
