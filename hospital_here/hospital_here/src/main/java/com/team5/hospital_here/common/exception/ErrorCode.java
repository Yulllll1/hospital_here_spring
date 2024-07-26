package com.team5.hospital_here.common.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(400, "USER_NOT_FOUND", "사용자를 찾을 수 없음"),
    INVALID_USER_CREDENTIALS(401, "INVALID_USER_CREDENTIALS", "비밀번호가 틀렸습니다."),
    CHAT_ROOM_NOT_FOUND(404, "CHAT_ROOM_NOT_FOUND", "존재하지 않는 채팅방입니다"),
    CHAT_ROOM_ACCESS_FAILED(400, "CHAT_ROOM_ACCESS_FAILED", "채팅방에 접근할 수 없습니다"),
    CHAT_ROOM_ALREADY_BELONG(400, "CHAT_ROOM_ALREADY_BELONG", "이미 참여중인 채팅방입니다"),
    CHAT_ROOM_NOT_BELONG(400, "CHAT_ROOM_NOT_BELONG", "속해있지 않은 채팅방입니다"),
    CHAT_MESSAGE_NOT_CONTENT(400, "CHAT_MESSAGE_NOT_CONTENT", "요청된 메시지가 없습니다"),
    CHAT_MESSAGE_NOT_FOUND(404, "CHAT_MESSAGE_NOT_FOUND", "존재하지 않는 메시지입니다"),
    USER_ALREADY_EXISTS(400, "USER_ALREADY_EXISTS", "사용자가 이미 존재합니다."),
    LOGIN_EMAIL_ALREADY_EXISTS(400, "LOGIN_EMAIL_ALREADY_EXISTS", "이미 사용중인 이메일 입니다."),
    USER_NAME_ALREADY_EXISTS(400, "USER_NAME_ALREADY_EXISTS", "이미 사용중인 이름 입니다."),
    NO_PERMISSION(403,"NO_PERMISSION","접근 권한이 없습니다."),
    ALREADY_DOCTOR_USER(400, "ALREADY_DOCTOR_USER", "이미 의사로 등록된 회원입니다."),
    CHAT_ROOM_NOT_EXIST(404, "CHAT_ROOM_NOT_EXIST", "채팅방 목록이 없습니다"),
    ACCESS_TOKEN_EXPIRED(401, "ACCESS_TOKEN_EXPIRED", "엑세스 토큰이 만료되었습니다."),
    REFRESH_TOKEN_EXPIRED(401, "REFRESH_TOKEN_EXPIRED", "리프레시 토큰이 만료되었습니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "REFRESH_TOKEN_NOT_FOUND", "발급된 리프레시 토큰이 없습니다."),
    INVALID_REFRESH_TOKEN(401, "INVALID_REFRESH_TOKEN", "토큰 정보가 일치하지 않습니다."),

    INVALID_SOCIAL_TOKEN(401,"INVALID_SOCIAL_TOKEN","토큰 정보가 일치하지 않습니다."),
    SOCIAL_USER(400,"SOCIAL_USER","소셜 가입 유저입니다."),

    BOARD_NOT_FOUND(400,"BOARD_NOT_FOUND","게시판을 찾을 수 없습니다."),
    POST_NOT_FOUND(400,"POST_NOT_FOUND","게시글을 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(400,"COMMENT_NOT_FOUND","댓글을 찾을 수 없습니다."),
    NESTED_COMMENT_NOT_FOUND(400,"NESTED_COMMENT_NOT_FOUND","대댓글을 찾을 수 없습니다.")
    ;

    //NOTE: HttpStatus code
    public final int code;
    
    //NOTE: code 의미
    public final String codeName;

    //NOTE: code 상세 메세지 
    public final String message;

    ErrorCode(int code, String codeName, String message) {
        this.code = code;
        this.codeName = codeName;
        this.message = message;
    }
}
