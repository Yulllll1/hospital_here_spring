package com.team5.hospital_here.board.dto.post;

import com.team5.hospital_here.board.domain.PostImg;
import lombok.*;

import java.util.List;

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
    private List<PostImg> imgUrls;
}
