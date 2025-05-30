package com.kernel.app.exception.dto;

import com.kernel.app.exception.ErrorCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final boolean success;
    private final String code;
    private final String message;
    private final LocalDateTime timestamp;

    public ErrorResponse(ErrorCode errorCode) {
        this.success = false;
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
        this.timestamp = LocalDateTime.now();
    }

    public ErrorResponse(String code, String message) {
        this.success = false;
        this.code = code;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

}