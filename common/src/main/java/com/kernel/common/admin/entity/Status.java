package com.kernel.common.admin.entity;

public enum Status {
    // TODO: Status Enum 정의
    ACTIVE,     // 활성 상태
    INACTIVE,   // 비활성 상태 (예: 휴면 계정)
    PENDING,    // 매니저 승인 대기 중 상태
    REJECTED,   // 매니저 승인 거부된 상태
    SUSPENDED,  // 신고된 매니저 자격 중지 상태
    DELETED     // 탈퇴 상태
}
