package com.kernel.payment.common.exception;

import com.kernel.payment.common.enums.PaymentErrorCode;
import lombok.Getter;

@Getter
public class InvalidPaymentAmountException extends RuntimeException {

    private final PaymentErrorCode errorCode;

    public InvalidPaymentAmountException(PaymentErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}