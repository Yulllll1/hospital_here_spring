package com.team5.hospital_here.board.domain;

import com.team5.hospital_here.board.dto.postImg.PostImgResponseDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PostImg {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String link;

    public void updateUrl(String url) {
        this.link = url;
    }

    public PostImgResponseDto toResponseDto() {
        return PostImgResponseDto.builder()
                .id(this.id)
                .postId(this.post.getId())
                .link(this.link)
                .build();
    }
}
