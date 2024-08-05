package com.team5.hospital_here.board.dto.comment;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentUpdateDto {
    private Long id;
    private String content;
}
