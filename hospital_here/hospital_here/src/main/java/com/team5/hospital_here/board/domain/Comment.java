package com.team5.hospital_here.board.domain;

import com.team5.hospital_here.common.baseEntity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Comment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private String author;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
