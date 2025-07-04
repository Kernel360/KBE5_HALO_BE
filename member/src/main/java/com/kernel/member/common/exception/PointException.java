package com.kernel.member.common.exception;

import com.kernel.member.common.enums.MemberErrorCode;
import lombok.Getter;

@Getter
public class PointException extends RuntimeException {

    private final MemberErrorCode errorCode;

    public PointException(MemberErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
