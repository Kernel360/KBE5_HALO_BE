package com.kernel.evaluation.common.exception;

import com.kernel.evaluation.common.enums.ReviewErrorCode;
import lombok.Getter;

@Getter
public class ReviewException extends RuntimeException {

    private final ReviewErrorCode errorCode;

    public ReviewException(ReviewErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
