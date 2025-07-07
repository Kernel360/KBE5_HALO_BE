package com.kernel.inquiry.common.exception;

import com.kernel.global.common.enums.ErrorCodeInterface;
import lombok.Getter;

@Getter
public class InquiryException extends RuntimeException {
    
    private final ErrorCodeInterface errorCode;
    
    public InquiryException(ErrorCodeInterface errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public InquiryException(ErrorCodeInterface errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
}