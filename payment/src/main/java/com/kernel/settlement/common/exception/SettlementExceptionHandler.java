package com.kernel.settlement.common.exception;

import com.kernel.global.service.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class SettlementExceptionHandler {

  /**
   * 문의사항 관련 공통 예외 처리
   *
   * @param ex InquiryException
   * @return ErrorResponse with error message
   */
  @ExceptionHandler(SettlementException.class)
  public ResponseEntity<ErrorResponse> handleInquiryException(SettlementException ex) {
    log.warn("Inquiry error: {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
  }

}