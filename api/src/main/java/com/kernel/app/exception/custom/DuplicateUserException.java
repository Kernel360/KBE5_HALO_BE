package com.kernel.app.exception.custom;

import com.kernel.app.exception.ErrorCode;

public class DuplicateUserException extends AuthException {
    public DuplicateUserException() {
        super(ErrorCode.DUPLICATE_USER);    // 이미 존재하는 사용자입니다.
    }
}