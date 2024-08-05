package com.team5.hospital_here.board.dto.postImg;

import com.team5.hospital_here.board.domain.Post;
import com.team5.hospital_here.board.domain.PostImg;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostImgRequestDto {
    private Long postId;
    private String link;

    public PostImg toEntity(Post post) {
        return PostImg.builder()
                .post(post)
                .link(this.link)
                .build();
    }
}
