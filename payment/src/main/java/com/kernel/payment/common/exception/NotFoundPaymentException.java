package com.kernel.payment.common.exception;

import com.kernel.payment.common.enums.PaymentErrorCode;
import lombok.Getter;

@Getter
public class NotFoundPaymentException extends RuntimeException {

    private final PaymentErrorCode errorCode;

    public NotFoundPaymentException(PaymentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
