package com.team5.hospital_here.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

public enum ErrorCode {
    USER_ALREADY_EXISTS(400, "USER_ALREADY_EXISTS", "사용자가 이미 존재함"),
    USER_NOT_FOUND(400, "USER_NOT_FOUND", "사용자를 찾을 수 없음"),
    LOGIN_PASSWORD_WRONG(400, "LOGIN_PASSWORD_WRONG", "잘못된 비밀번호를 입력했습니다."),
    NO_PERMISSION(403,"NO_PERMISSION","권한없음"),
    ;

    //NOTE: HttpStatus code
    public int code;
    
    //NOTE: code 의미
    public String codeName;
    
    //NOTE: code 상세 메세지 
    public String message;

    ErrorCode(int code, String codeName, String message) {
        this.code = code;
        this.codeName = codeName;
        this.message = message;
    }
}
