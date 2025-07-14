package com.kernel.global.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode implements ErrorCodeInterface {

    // 회원가입/인증 관련
    DUPLICATE_USER(400, "AUTH001", "이미 존재하는 사용자입니다."),
    INVALID_SIGNUP_DATA(400, "AUTH002", "회원가입 정보가 올바르지 않습니다."),
    USER_NOT_FOUND(404, "AUTH006", "존재하지 않는 사용자입니다."),

    // 로그인 관련
    INVALID_CREDENTIALS(401, "AUTH003", "아이디 또는 비밀번호가 올바르지 않습니다."),
    ACCOUNT_LOCKED(401, "AUTH004", "계정이 잠겨있습니다."),
    INVALID_PASSWORD(400, "AUTH005", "비밀번호가 올바르지 않습니다."),

    // OAuth2 관련
    INVALID_USER_INFO(400, "AUTH008", "필수 사용자 정보가 누락되었습니다."),
    DUPLICATE_PHONE(400, "AUTH009", "이미 등록된 전화번호입니다."),
    OAUTH2_PROVIDER_ERROR(400, "AUTH010", "OAuth2 제공자에서 오류가 발생했습니다."),

    // JWT 토큰 관련
    INVALID_TOKEN(401, "JWT001", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(401, "JWT002", "만료된 토큰입니다."),
    REFRESH_TOKEN_NOT_FOUND(401, "JWT003", "리프레시 토큰을 찾을 수 없습니다."),

    // 권한 관련
    ACCESS_DENIED(403, "AUTH007", "접근 권한이 없습니다."),

    // 서버 에러
    INTERNAL_SERVER_ERROR(500, "SRV001", "서버 내부 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;

}