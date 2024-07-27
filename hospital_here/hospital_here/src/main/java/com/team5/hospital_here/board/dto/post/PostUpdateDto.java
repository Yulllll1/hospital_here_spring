package com.team5.hospital_here.board.dto.post;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {
    private Long id;
    private Long boardId;
    private Long userId;
    private String title;
    private String content;
}
