package com.team5.hospital_here.board.dto.postImg;

import com.team5.hospital_here.board.domain.PostImg;
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