package com.kernel.global.common.handler;

import com.kernel.global.common.exception.AuthException;
import com.kernel.global.service.dto.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiResponse<Void>> handleUserNotFoundException(AuthException e) {
        log.error("User not found: {}", e.getMessage(), e);
        ApiResponse<Void> response = new ApiResponse<>(false, e.getMessage(), null);
        return ResponseEntity.status(404).body(response);
    }
}
