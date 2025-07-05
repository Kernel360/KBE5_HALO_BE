package com.kernel.reservation.common.exception;

import com.kernel.reservation.common.enums.ReservationErrorCode;
import lombok.Getter;

@Getter
public class NoAvailableManagerException extends RuntimeException {

    private final ReservationErrorCode errorCode;

    public NoAvailableManagerException(ReservationErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}