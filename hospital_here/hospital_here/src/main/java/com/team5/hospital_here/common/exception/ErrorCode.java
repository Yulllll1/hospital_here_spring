package com.team5.hospital_here.common.exception;


import lombok.Getter;

public enum ErrorCode {
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
