package com.team5.hospital_here.common.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponse> customeExceptionHandler(CustomException exception){
        ExceptionResponse response = exception.toResponse();
        ErrorCode code = exception.getErrorCode();
        log.error("### ERROR!! code: {}, code name: {}, code message: {}", code.code, code.codeName, code.message);

        return new ResponseEntity<>(response, HttpStatus.valueOf(code.code));
    }

}
