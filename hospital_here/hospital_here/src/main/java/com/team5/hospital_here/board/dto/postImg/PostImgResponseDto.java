package com.team5.hospital_here.board.dto.postImg;

import com.team5.hospital_here.board.domain.PostImg;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class PostImgResponseDto {
    private Long id;
    private Long postId;
    private String link;

    @Builder
    public PostImgResponseDto(Long id, Long postId, String link) {
        this.id = id;
        this.postId = postId;
        this.link = link;
    }
}