package com.kernel.settlement.common.exception;

import lombok.Getter;

@Getter
public class SettlementException extends RuntimeException {

    private final SettlementErrorCode errorCode;

    public SettlementException(SettlementErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
