package com.kernel.reservation.common.handler;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.reservation.common.exception.ServiceCheckLogException;
import com.kernel.reservation.service.response.common.ServiceCheckLogErrorRspDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ServiceCheckLogExceptionHandler {

    @ExceptionHandler(ServiceCheckLogException.class)
    public ResponseEntity<ServiceCheckLogErrorRspDTO> handleServiceCheckLogException(ServiceCheckLogException e) {
        log.error("ServiceCheckLogException error: {}", e.getMessage());

        return ResponseEntity.status(e.getErrorCode().getStatus()).body(ServiceCheckLogErrorRspDTO.createErrorResponse(e));
    }

}
