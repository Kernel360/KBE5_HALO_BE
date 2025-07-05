package com.kernel.payment.common.handler;

import com.kernel.global.service.dto.response.ErrorResponse;
import com.kernel.payment.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class PaymentExceptionHandler {

    // 결제 내역 없음 예외
    @ExceptionHandler(NotFoundPaymentException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundPaymentException(NotFoundPaymentException e) {
        log.warn("NotFoundPaymentException error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    // 결제 실패 예외
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException e) {
        log.error("PaymentException error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    // 잘못된 결제 금액 예외
    @ExceptionHandler(InvalidPaymentAmountException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPaymentAmountException(InvalidPaymentAmountException e) {
        log.warn("InvalidPaymentAmountException error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    // 이미 결제된 예약 예외
    @ExceptionHandler(AlreadyPaidException.class)
    public ResponseEntity<ErrorResponse> handleAlreadyPaidException(AlreadyPaidException e) {
        log.warn("AlreadyPaidException error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    // 포인트 부족 예외
    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientPointsException(InsufficientPointsException e) {
        log.warn("InsufficientPointsException error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

    // 결제 취소 불가 예외
    @ExceptionHandler(PaymentCancelNotAllowedException.class)
    public ResponseEntity<ErrorResponse> handlePaymentCancelNotAllowedException(PaymentCancelNotAllowedException e) {
        log.warn("PaymentCancelNotAllowedException error: {}", e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(e.getErrorCode());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }

}
