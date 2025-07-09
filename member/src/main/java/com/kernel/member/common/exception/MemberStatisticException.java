package com.kernel.member.common.exception;

import com.kernel.member.common.enums.MemberStatisticErrorCode;
import lombok.Getter;

@Getter
public class MemberStatisticException extends RuntimeException {
    private final MemberStatisticErrorCode errorCode;

    public MemberStatisticException(MemberStatisticErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
