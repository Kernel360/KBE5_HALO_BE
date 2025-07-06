package com.kernel.inquiry.common.enums;

public enum ManagerInquiryCategory implements InquiryCategory {
    SCHEDULE("스케줄 조정"),
    PAYMENT("정산/급여"),
    WORK_PROBLEM("업무 고충"),
    SYSTEM("시스템 문제"),
    ETC("기타 문의");

    private final String label;

    ManagerInquiryCategory(String label) {
        this.label = label;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return label;
    }
}