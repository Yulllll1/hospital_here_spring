package com.team5.hospital_here.common.exception;


import lombok.Getter;

@Getter
public class CustomException extends RuntimeException{

    private ErrorCode errorCode;

    public CustomException(ErrorCode errorCode){
        super(errorCode.message);
        this.errorCode = errorCode;
    }

    public ExceptionResponse toResponse(){
        return new ExceptionResponse(errorCode);
    }
}
