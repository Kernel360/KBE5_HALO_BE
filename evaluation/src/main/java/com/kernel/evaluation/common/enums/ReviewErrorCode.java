package com.kernel.evaluation.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ReviewErrorCode {

    UNAUTHORIZED(403, "REVIEW-001", "리뷰 작성 권한이 없습니다."),
    ALREADY_EXISTS_REVIEW(409, "REVIEW-002", "이미 리뷰가 작성되어 있습니다."),
    NOT_COMPLETED_RESERVATION(400, "REVIEW-003", "완료되지 않은 예약에 대해서는 리뷰를 작성할 수 없습니다.");

    private final int status;
    private final String code;
    private final String message;
}
