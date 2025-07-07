package com.kernel.member.common.handler;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.common.exception.AvailableTimeException;
import com.kernel.member.common.exception.SpecialtyException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.awt.print.PrinterException;

@RestControllerAdvice
@Slf4j
public class MemberExceptionHandler {

    // 포인트 예외 처리
    @ExceptionHandler(PrinterException.class)
    public ResponseEntity<ApiResponse<Void>> handlePrinterException(PrinterException e) {
        log.warn("PrinterException error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
    }

    // 매니저 회원가입, 매니저 정보 수정 예외 처리
    @ExceptionHandler(SpecialtyException.class)
    public ResponseEntity<ApiResponse<Void>> handleSpecialtyException(SpecialtyException e) {
        log.error("SpecialtyException error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
    }

    @ExceptionHandler(AvailableTimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleAvailableTimeException(AvailableTimeException e) {
        log.error("AvailableTimeException error: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse<>(false, e.getMessage(), null));
    }
}
