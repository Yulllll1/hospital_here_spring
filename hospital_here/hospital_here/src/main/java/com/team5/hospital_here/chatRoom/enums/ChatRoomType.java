package com.team5.hospital_here.chatRoom.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum ChatRoomType {

    DOCTOR("의사 상담"),
    AI("AI 상담"),
    SERVICE("서비스센터 상담");

    private final String type;
}
