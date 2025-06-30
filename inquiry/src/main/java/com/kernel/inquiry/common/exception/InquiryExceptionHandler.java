package com.kernel.inquiry.common.exception;

import com.kernel.global.service.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InquiryExceptionHandler {

  /**
   * 커스텀 예외 처리: 데이터가 없는 경우
   *
   * @param ex CustomNoDataFoundException
   * @return ApiResponse<Void> with error message
   */
  @ExceptionHandler(CustomNoDataFoundException.class)
  public ResponseEntity<ApiResponse<Void>> handleNoDataFoundException(CustomNoDataFoundException ex) {
    return ResponseEntity.ok(new ApiResponse<>(true, "조건에 맞는 데이터가 없습니다.", null));
  }
}