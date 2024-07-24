package com.team5.hospital_here.common.exception;


import lombok.Getter;
@Getter
public enum ErrorCode {
    BOARD_NOT_FOUND(400,"BOARD_NOT_FOUND","BOARD를 찾을 수 없습니다."),
    POST_NOT_FOUND(400,"POST_NOT_FOUND","POST를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(400,"COMMENT_NOT_FOUND","COMMENT를 찾을 수 없습니다."),
    USER_NOT_FOUND(400,"USER_NOT_FOUND","USER를 찾을 수 없습니다."),
    ;

    public int code;
    public String codeName;
    public String message;

    ErrorCode(int code, String codeName, String message) {
        this.code = code;
        this.codeName = codeName;
        this.message = message;
    }
}