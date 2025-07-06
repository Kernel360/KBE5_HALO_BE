package com.kernel.member.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum MemberErrorCode {

    // point 관련
    POINT_EXCEED_LIMIT(400, "POINT-001", "보유 가능한 최대 포인트는 1,000,000원을 초과할 수 없습니다."),

    // Specialty 관련
    SPECIALTY_NOT_FOUND(404, "SPECIALTY-001", "전문 분야를 찾을 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
