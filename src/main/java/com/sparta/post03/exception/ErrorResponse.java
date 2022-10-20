package com.sparta.post03.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {

    private int errorCode;
    private String errorMessage;
    private String errorDetail;


    public static ErrorResponse of(HttpStatus httpStatus, String message, String errorDetail) {
        return new ErrorResponse(httpStatus.value(), message, errorDetail); //밑에 생성자를 반환함
    }
    public ErrorResponse(int errorCode, String message, String errorDetail) {
        this.errorCode = errorCode;
        this.errorMessage = message;
        this.errorDetail = errorDetail;
    }
}