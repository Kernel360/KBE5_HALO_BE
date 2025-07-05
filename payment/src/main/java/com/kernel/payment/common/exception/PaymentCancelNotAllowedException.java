package com.kernel.payment.common.exception;

import com.kernel.payment.common.enums.PaymentErrorCode;
import lombok.Getter;

@Getter
public class PaymentCancelNotAllowedException extends RuntimeException {

    private final PaymentErrorCode errorCode;

    public PaymentCancelNotAllowedException(PaymentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}