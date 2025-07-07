package com.kernel.inquiry.common.handler;

import com.kernel.global.service.dto.response.ErrorResponse;
import com.kernel.inquiry.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class InquiryExceptionHandler {

  /**
   * 문의사항 관련 공통 예외 처리
   *
   * @param ex InquiryException
   * @return ErrorResponse with error message
   */
  @ExceptionHandler(InquiryException.class)
  public ResponseEntity<ErrorResponse> handleInquiryException(InquiryException ex) {
    log.warn("Inquiry error: {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
  }

  /**
   * 커스텀 예외 처리: 데이터가 없는 경우
   *
   * @param ex CustomNoDataFoundException
   * @return ErrorResponse with error message
   */
  @ExceptionHandler(CustomNoDataFoundException.class)
  public ResponseEntity<ErrorResponse> handleNoDataFoundException(CustomNoDataFoundException ex) {
    log.warn("CustomNoDataFoundException : {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
  }

  /**
   * 커스텀 예외 처리: 문의사항을 찾을 수 없는 경우
   *
   * @param ex InquiryNotFoundException
   * @return ErrorResponse with error message
   */
  @ExceptionHandler(InquiryNotFoundException.class)
  public ResponseEntity<ErrorResponse> handleInquiryNotFoundException(InquiryNotFoundException ex) {
    log.warn("InquiryNotFoundException : {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
  }

  /**
   * 커스텀 예외 처리: 잘못된 문의사항 카테고리
   *
   * @param ex InvalidInquiryCategoryException
   * @return ErrorResponse with error message
   */
  @ExceptionHandler(InvalidInquiryCategoryException.class)
  public ResponseEntity<ErrorResponse> handleInvalidInquiryCategoryException(InvalidInquiryCategoryException ex) {
    log.warn("InvalidInquiryCategoryException : {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
  }

  /**
   * 커스텀 예외 처리: 중복 답변
   *
   * @param ex DuplicateReplyException
   * @return ErrorResponse with error message
   */
  @ExceptionHandler(DuplicateReplyException.class)
  public ResponseEntity<ErrorResponse> handleDuplicateReplyException(DuplicateReplyException ex) {
    log.warn("Duplicate reply: {}", ex.getMessage());
    ErrorResponse errorResponse = new ErrorResponse(ex.getErrorCode());
    return ResponseEntity.status(ex.getErrorCode().getStatus()).body(errorResponse);
  }
}