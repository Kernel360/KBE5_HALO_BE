package com.kernel.evaluation.common.handler;

import com.kernel.evaluation.common.exception.ReviewException;
import com.kernel.evaluation.service.review.dto.common.response.ReviewErrorRspDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ReviewExceptionHandler {

    @ExceptionHandler(ReviewException.class)
    public ResponseEntity<ReviewErrorRspDTO> handleReviewException(ReviewException e) {
        log.error("ReviewException error: {}", e.getMessage());

        ReviewErrorRspDTO errorResponse = ReviewErrorRspDTO.createErrorResponse(e);
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(errorResponse);
    }
}
