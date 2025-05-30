package com.kernel.app.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {

    // 회원가입/인증 관련
    DUPLICATE_USER(400, "AUTH001", "이미 존재하는 사용자입니다."),
    INVALID_SIGNUP_DATA(400, "AUTH002", "회원가입 정보가 올바르지 않습니다."),

    // 로그인 관련
    INVALID_CREDENTIALS(401, "AUTH003", "아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCOUNT_LOCKED(401, "AUTH004", "계정이 잠겨있습니다."),

    // JWT 토큰 관련
    INVALID_TOKEN(401, "JWT001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401, "JWT002", "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "JWT003", "리프레시 토큰을 찾을 수 없습니다."),

    // 권한 관련
    ACCESS_DENIED(403, "AUTH005", "접근 권한이 없습니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR(500, "SRV001", "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

}