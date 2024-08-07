package com.team5.hospital_here.board.dto.comment;

import lombok.*;

import java.time.LocalDateTime;

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
    private String userName;
    private LocalDateTime createdAt;
}
