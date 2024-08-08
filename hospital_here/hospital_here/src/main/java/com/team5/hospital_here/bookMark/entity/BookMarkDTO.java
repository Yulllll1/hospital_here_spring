package com.team5.hospital_here.bookMark.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class BookMarkDTO {

    private Long id;
    private Long userId;
    private Long hospitalId;
}
