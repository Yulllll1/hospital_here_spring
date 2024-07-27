package com.team5.hospital_here.common.exception;


import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.service.annotation.GetExchange;

@Getter
public class ExceptionResponse {

    private int code;
    private String codeName;
    private String message;

    public ExceptionResponse(ErrorCode errorCode){
        code = errorCode.code;
        codeName = errorCode.codeName;
        message = errorCode.message;
    }
}
