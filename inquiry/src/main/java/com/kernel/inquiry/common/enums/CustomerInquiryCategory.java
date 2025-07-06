package com.kernel.inquiry.common.enums;

public enum CustomerInquiryCategory implements InquiryCategory {
    RESERVATION("예약 및 일정 변경"),
    SERVICE("서비스 내용 문의"),
    PAYMENT("요금 및 결제"),
    COMPLAINT("불만 및 불편사항"),
    ETC("기타 문의");

    private String label;

    CustomerInquiryCategory(String label) {
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
