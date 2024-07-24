package com.team5.hospital_here.common.exception;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    USER_ALREADY_EXISTS(400, "USER_ALREADY_EXISTS", "사용자가 이미 존재합니다."),
    LOGIN_EMAIL_ALREADY_EXISTS(400, "LOGIN_EMAIL_ALREADY_EXISTS", "이미 사용중인 이메일 입니다."),
    USER_NAME_ALREADY_EXISTS(400, "USER_NAME_ALREADY_EXISTS", "이미 사용중인 이름 입니다."),
    USER_NOT_FOUND(400, "USER_NOT_FOUND", "사용자를 찾을 수 없습니다."),
    LOGIN_PASSWORD_WRONG(400, "LOGIN_PASSWORD_WRONG", "잘못된 비밀번호를 입력했습니다."),
    WRONG_PASSWORD(400, "WRONG_PASSWORD", "잘못된 비밀번호를 입력했습니다."),
    NO_PERMISSION(403,"NO_PERMISSION","접근 권한이 없습니다."),
    ALREADY_DOCTOR_USER(400, "ALREADY_DOCTOR_USER", "이미 의사로 등록된 회원입니다.")
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
