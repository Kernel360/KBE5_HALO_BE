package com.kernel.reservation.common.exception;

import com.kernel.reservation.common.enums.ReservationErrorCode;
import lombok.Getter;

@Getter
public class InvalidReservationTimeException extends RuntimeException {

    private final ReservationErrorCode errorCode;

    public InvalidReservationTimeException(ReservationErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}