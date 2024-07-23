package com.team5.hospital_here.common.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public enum ErrorCode {
    CHAT_ROOM_NOT_FOUND(404, "NOT_FOUND", "존재하지 않는 채팅방입니다"),
    CHAT_ROOM_ACCESS_FAILED(404, "BAD_REQUEST", "채팅방에 접근할 수 없습니다");

    public int code;
    public String codeName;
    public String message;

    @Builder
    ErrorCode(int code, String codeName, String message) {
        this.code = code;
        this.codeName = codeName;
        this.message = message;
    }
}
