package com.kernel.global.common.exception;


import com.kernel.global.common.enums.ErrorCode;

public class DuplicateUserException extends AuthException {
    public DuplicateUserException() {
        super(ErrorCode.DUPLICATE_USER);    // 이미 존재하는 사용자입니다.
    }
}