package com.kernel.reservation.common.exception;

import com.kernel.reservation.common.enums.ServiceCheckLogErrorCode;
import lombok.Getter;

@Getter
public class ServiceCheckLogException extends RuntimeException {

    private final ServiceCheckLogErrorCode errorCode;

    public ServiceCheckLogException(ServiceCheckLogErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
