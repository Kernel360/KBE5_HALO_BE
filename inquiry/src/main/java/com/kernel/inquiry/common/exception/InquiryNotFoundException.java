package com.kernel.inquiry.common.exception;

import com.kernel.global.common.enums.ErrorCodeInterface;

public class InquiryNotFoundException extends InquiryException {
    
    public InquiryNotFoundException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }
    
    public InquiryNotFoundException(ErrorCodeInterface errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}