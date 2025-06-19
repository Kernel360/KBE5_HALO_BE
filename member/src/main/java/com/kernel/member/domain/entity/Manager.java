package com.kernel.member.domain.entity;

import com.kernel.member.domain.enumerate.ContractStatus;

import java.time.LocalDateTime;

public class Manager {

    // 매니저 ID
    private Long managerId;

    // 사용자 정보
    private UserInfo userInfo;

    // 특기
    //private ServiceCategory specialty;

    // 한 줄 소개
    private String bio;

    // 프로필 이미지 URL
    private long profileImageFileId;

    // 계약상태
    private ContractStatus contractStatus;

    // 계약 일시
    private LocalDateTime contractDate;

    // 계약 해지 요청 일시
    private String requestDate;

    // 계약 해지 사유
    private String reason;

    // 계약 해지 일시
    private String terminationDate;
}
