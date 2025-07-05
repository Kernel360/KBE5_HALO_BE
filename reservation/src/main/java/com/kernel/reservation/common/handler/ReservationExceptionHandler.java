package com.kernel.reservation.common.handler;


import com.kernel.reservation.common.exception.InsufficientPointsException;
import com.kernel.reservation.common.exception.NoAvailableManagerException;
import com.kernel.reservation.service.response.common.ReservationErrorResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
@Slf4j
public class ReservationExceptionHandler {

    // 포인트 부족 예외
    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<ReservationErrorResponse> handleAuthException(InsufficientPointsException e) {
        log.warn("InsufficientPointsException error: {}", e.getMessage());
        ReservationErrorResponse errorResponse = new ReservationErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    // 매니저 없음 예외
    @ExceptionHandler(NoAvailableManagerException.class)
    public ResponseEntity<ReservationErrorResponse> handleNoAvailableManagerException(NoAvailableManagerException e) {
        log.warn("NoAvailableManagerException error: {}", e.getMessage());
        ReservationErrorResponse errorResponse = new ReservationErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }




}
