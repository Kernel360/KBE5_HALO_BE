package com.kernel.payment.common.enums;

import com.kernel.global.common.enums.ErrorCodeInterface;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum PaymentErrorCode implements ErrorCodeInterface {

    // 결제 내역 없음
    NOT_FOUND_PAYMENT(404,"PAYMENT-001","결제내역이 존재하지 않습니다."),
    
    // 결제 실패
    PAYMENT_FAILED(400,"PAYMENT-002","결제 처리에 실패했습니다."),
    
    // 잘못된 결제 금액
    INVALID_PAYMENT_AMOUNT(400,"PAYMENT-003","결제 금액이 올바르지 않습니다."),
    
    // 이미 결제된 예약
    ALREADY_PAID_RESERVATION(400,"PAYMENT-004","이미 결제가 완료된 예약입니다."),
    
    // 결제 취소 불가
    PAYMENT_CANCEL_NOT_ALLOWED(400,"PAYMENT-005","결제 취소가 불가능합니다."),
    
    // 포인트 부족
    INSUFFICIENT_POINTS(400,"PAYMENT-006","포인트가 부족합니다."),
    
    // 결제 방법 오류
    INVALID_PAYMENT_METHOD(400,"PAYMENT-007","지원하지 않는 결제 방법입니다."),
    
    // 결제 서버 오류
    PAYMENT_SERVER_ERROR(500,"PAYMENT-008","결제 서버에서 오류가 발생했습니다.");

    private final int status;
    private final String code;
    private final String message;
}
