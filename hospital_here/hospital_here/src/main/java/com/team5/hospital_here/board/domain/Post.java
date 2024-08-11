package com.team5.hospital_here.board.domain;

import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.common.baseEntity.BaseEntity;
import com.team5.hospital_here.user.entity.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotEmpty(message = "필수 입력값 입니다.")
    private String title;

    @NotEmpty(message = "필수 입력값 입니다.")
    private String content;

    @Builder.Default
    private Long viewCount = 0L;

    @Builder.Default
    private Long likeCount = 0L;

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImg> postImgs = new ArrayList<>();

    public void update(PostUpdateDto postUpdateDto) {
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
        if (postUpdateDto.getImageUrls() != null) {
            this.updateImages(postUpdateDto.getImageUrls());
        }
    }

    private void updateImages(List<String> imageUrls) {
        this.postImgs.clear();
        for (String imageUrl : imageUrls) {
            PostImg postImg = PostImg.builder()
                    .link(imageUrl)
                    .post(this)
                    .build();
            this.postImgs.add(postImg);
        }
    }

    public void incrementViewCount() {
        this.viewCount++;
    }

    public void incrementLikeCount() {
        this.likeCount++;
    }

    public void decrementLikeCount() {
        this.likeCount--;
    }

    public void addPostImg(PostImg postImg) {
        if (this.postImgs == null) {
            this.postImgs = new ArrayList<>();
        }
        this.postImgs.add(postImg);
        postImg.setPost(this);
    }

    public PostResponseDto toResponseDto() {
        return PostResponseDto.builder()
                .id(this.id)
                .boardId(this.board.getId())
                .userId(this.user.getId())
                .userName(this.user.getName())
                .title(this.title)
                .content(this.content)
                .viewCount(this.viewCount)
                .likeCount(this.likeCount)
                .imageUrls(this.postImgs.stream()
                        .map(PostImg::toResponseDto)
                        .toList())
                .createdAt(this.getCreatedAt())
                .updatedAt(this.getUpdatedAt())
                .build();
    }
}

