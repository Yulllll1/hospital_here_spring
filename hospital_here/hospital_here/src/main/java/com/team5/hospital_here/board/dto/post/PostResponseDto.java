package com.team5.hospital_here.board.dto.post;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseDto {
    private Long id;
    private Long boardId;
    private Long userId;
    private String title;
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