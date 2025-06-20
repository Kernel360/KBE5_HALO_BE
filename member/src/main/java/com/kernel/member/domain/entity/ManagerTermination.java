package com.kernel.member.domain.entity;

public class ManagerTermination {
    // 매니저 ID
    private Long managerId;

    // 계약 해지 요청 일시
    private String requestDate;

    // 계약 해지 사유
    private String reason;

    // 계약 해지 일시
    private String terminationDate;
}
