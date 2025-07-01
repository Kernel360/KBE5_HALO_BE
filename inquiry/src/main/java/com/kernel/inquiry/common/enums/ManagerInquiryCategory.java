package com.kernel.inquiry.common.enums;

public enum ManagerInquiryCategory {
    SCHEDULE("스케줄 조정"),
    PAYMENT("정산 / 급여"),
    WORK_PROBLEM("업무 고충"),
    SYSTEM("시스템 문제"),
    ETC("기타 문의");

    private String categoryName;

    ManagerInquiryCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
