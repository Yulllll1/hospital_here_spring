package com.team5.hospital_here.board.dto.post;

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
}