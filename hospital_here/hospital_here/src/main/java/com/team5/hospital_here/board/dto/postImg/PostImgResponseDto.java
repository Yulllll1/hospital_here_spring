package com.team5.hospital_here.board.dto.postImg;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImgResponseDto {
    private Long id;
    private Long postId;
    private String link;

}