package com.team5.hospital_here.chatRoom.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum ChatRoomStatus {

    WAITING("수락 대기", "채팅 수락을 기다리는 상태입니다"),
    ACTIVE("진행", "채팅이 진행중인 상태입니다"),
    INACTIVE("비활성화", "상대방이 채팅을 종료한 상태입니다");

    private final String status;
    private final String detail;
}
