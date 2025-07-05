package com.kernel.inquiry.common.enums;

import com.kernel.global.common.enums.ErrorCodeInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum InquiryErrorCode implements ErrorCodeInterface {

    // 400 Bad Request
    INVALID_INQUIRY_CATEGORY(400, "INQ-001", "잘못된 문의사항 카테고리입니다."),
    UNSUPPORTED_USER_TYPE(400, "INQ-002", "사용할 수 없는 사용자 타입입니다."),

    // 403 Forbidden
    INQUIRY_ACCESS_DENIED(403, "INQ-101", "문의사항에 대한 접근 권한이 없습니다."),

    // 404 Not Found
    INQUIRY_NOT_FOUND(404, "INQ-201", "문의사항을 찾을 수 없습니다."),
    REPLY_NOT_FOUND(404, "INQ-202", "답변을 찾을 수 없습니다."),
    NO_DATA_FOUND(404, "INQ-203", "조건에 맞는 데이터가 없습니다."),

    // 409 Conflict
    DUPLICATE_REPLY(409, "INQ-301", "이미 답변이 존재합니다.");

    private final int status;
    private final String code;
    private final String message;
}