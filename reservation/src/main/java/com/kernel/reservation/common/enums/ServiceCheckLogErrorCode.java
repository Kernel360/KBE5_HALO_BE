package com.kernel.reservation.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ServiceCheckLogErrorCode {

    CHECK_IN_ALREADY_EXISTS(400, "SERVICE-CHECK-LOG-1", "체크인 정보가 이미 존재합니다."),
    CHECK_OUT_ALREADY_EXISTS(400, "SERVICE-CHECK-LOG-2", "체크아웃 정보가 이미 존재합니다."),
    CHECK_IN_NOT_FOUND(404, "SERVICE-CHECK-LOG-3", "체크인 정보를 찾을 수 없습니다."),
    CHECK_OUT_NOT_FOUND(404, "SERVICE-CHECK-LOG-4", "체크아웃 정보를 찾을 수 없습니다."),
    CHECK_IN_FILE_NOT_FOUND(404, "SERVICE-CHECK-LOG-5", "체크인 첨부파일을 찾을 수 없습니다."),
    CHECK_OUT_FILE_NOT_FOUND(404, "SERVICE-CHECK-LOG-6", "체크아웃 첨부파일을 찾을 수 없습니다."),
    CHECK_IN_FILE_UPLOAD_FAILED(500, "SERVICE-CHECK-LOG-7", "체크인 첨부파일 업로드에 실패했습니다."),
    CHECK_OUT_FILE_UPLOAD_FAILED(500, "SERVICE-CHECK-LOG-8", "체크아웃 첨부파일 업로드에 실패했습니다."),
    CHECK_IN_NOT_ALLOWED(400, "SERVICE-CHECK-LOG-9", "체크아웃을 하지 않은 예약이 존재합니다.");

    private final int status;
    private final String code;
    private final String message;
}
