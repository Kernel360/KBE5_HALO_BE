package com.kernel.inquiry.common.exception;

import com.kernel.global.common.enums.ErrorCodeInterface;

public class CustomNoDataFoundException extends InquiryException {

    public CustomNoDataFoundException(ErrorCodeInterface errorCode) {
        super(errorCode);
    }
    
    public CustomNoDataFoundException(ErrorCodeInterface errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}
