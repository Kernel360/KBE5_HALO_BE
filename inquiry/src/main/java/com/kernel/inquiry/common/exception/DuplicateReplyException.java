package com.kernel.inquiry.common.exception;

import com.kernel.global.common.enums.ErrorCodeInterface;

public class DuplicateReplyException extends InquiryException {
    
    public DuplicateReplyException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }
    
    public DuplicateReplyException(ErrorCodeInterface errorCode, Throwable cause) {
        super(errorCode, cause);
    }

}