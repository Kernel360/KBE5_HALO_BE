package com.kernel.inquiry.common.exception;

import com.kernel.global.common.enums.ErrorCodeInterface;

public class InvalidInquiryCategoryException extends InquiryException {
    
    public InvalidInquiryCategoryException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }
    
    public InvalidInquiryCategoryException(ErrorCodeInterface errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}