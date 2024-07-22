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
    private String parentId;
    private String content;
}
/*
public Board toEntity() {
        return Board.builder()
                .id(this.id)
                .name(this.name)
                .build();
    }
 */