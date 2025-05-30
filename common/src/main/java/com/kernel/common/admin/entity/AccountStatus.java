package com.kernel.common.admin.entity;

// 수요자가 신고된 상태인지 확인 하는 것 추가하기
public enum AccountStatus {
        ACTIVE("활성"),
        REJECTED("승인 거절"),
        SUSPENDED("정지"),
        DELETED("탈퇴"),
        PENDING("승인 대기");

    private String MemberStatus;

    AccountStatus(String genderName) {
        this.MemberStatus = genderName;
    }

    public String getMemberStatus() {
        return MemberStatus;
    }
}
