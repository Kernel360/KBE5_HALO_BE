package com.kernel.inquiry.common.enums;

public enum CustomerInquiryCategory {
    RESERVATION_CHANGE("예약 및 일정 변경"),
    SERVICE_INQUIRY("서비스 내용 문의"),
    PAYMENT_ISSUE("요금 및 결제"),
    COMPLAINT("불만 및 불편사항"),
    ETC("기타 문의");

    private String categoryName;

    CustomerInquiryCategory(String categoryName) {
        this.categoryName = categoryName;
    }
}
