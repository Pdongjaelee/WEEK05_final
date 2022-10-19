package com.sparta.post03.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    //중복확인 예외처리
    @ExceptionHandler(DuplicateMemberException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateMemberException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.DUPLICATE_MEMBER_ID, ErrorCode.DUPLICATE_MEMBER_ID.getMessage(),ErrorCode.DUPLICATE_MEMBER_ID.getDetail());
        log.error("error: {}, stacktrace: {}", errorResponse, e.getStackTrace());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

   // password 예외처리
    @ExceptionHandler(passwordConfirmException.class)
    public ResponseEntity<ErrorResponse> handlePasswordConfirmException(Exception e) {
        ErrorResponse errorResponse = ErrorResponse.of(ErrorCode.BAD_PASSWORD_CONFIRM, ErrorCode.DUPLICATE_MEMBER_ID.getMessage(),ErrorCode.BAD_PASSWORD_CONFIRM.getDetail());
        log.error("error: {}, stacktrace: {}", errorResponse, e.getStackTrace());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
