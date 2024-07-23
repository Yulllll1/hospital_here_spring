package com.team5.hospital_here.board.domain;

import com.team5.hospital_here.board.dto.comment.CommentResponseDto;
import com.team5.hospital_here.board.dto.post.PostResponseDto;
import com.team5.hospital_here.board.dto.post.PostUpdateDto;
import com.team5.hospital_here.common.baseEntity.BaseEntity;
import jakarta.persistence.*;
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

    private String title;

    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostImg> postImgs = new ArrayList<>();

    public void update(PostUpdateDto postUpdateDto, Board board, User user) {
        this.board = board;
        this.user = user;
        this.title = postUpdateDto.getTitle();
        this.content = postUpdateDto.getContent();
    }

    public PostResponseDto toResponseDto() {
        return PostResponseDto.builder()
                .id(this.id)
                .boardId(this.board.getId())
                .userId(this.user.getId())
                .title(this.title)
                .content(this.content)
                .build();
    }
}

