package com.team5.hospital_here.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    USER_ALREADY_EXISTS(409, "USER_ALREADY_EXISTS", "사용자가 이미 존재함"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND", "사용자를 찾을 수 없음"),
    NO_PERMISSION(400,"NO_PERMISSION","권한없음"),
    INVALID_USER_CREDENTIALS(401, "INVALID_USER_CREDENTIALS", "비밀번호 틀림"),
    CHAT_ROOM_NOT_FOUND(404, "CHAT_ROOM_NOT_FOUND", "존재하지 않는 채팅방입니다"),
    CHAT_ROOM_ACCESS_FAILED(400, "CHAT_ROOM_ACCESS_FAILED", "채팅방에 접근할 수 없습니다"),
    CHAT_ROOM_ALREADY_BELONG(400, "CHAT_ROOM_ALREADY_BELONG", "이미 참여중인 채팅방입니다"),
    CHAT_ROOM_NOT_BELONG(400, "CHAT_ROOM_NOT_BELONG", "속해있지 않은 채팅방입니다");

    //NOTE: HttpStatus code
    public int code;
    
    //NOTE: code 의미
    public String codeName;
    
    //NOTE: code 상세 메세지
    public String message;

    @Builder
    ErrorCode(int code, String codeName, String message) {
        this.code = code;
        this.codeName = codeName;
        this.message = message;
    }
}
