package com.kernel.settlement.common.utils;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class SettlementCalculator {

    private final int feeRate;

    // 매니저용 수수료 계산
    public int calculatePlatformFee(int price) {
        return price * feeRate / 100;
    }

    // 관리자용 수수료 계산
    public Long calculatePlatformFee(Long price) {
        return price * feeRate / 100;
    }

    // 정산 계산
    public int calculateTotalAmount(int price) {
        return price * (100 - feeRate) / 100;
    }

    // 관리자 확인용 정산 계산
    public Long calculateTotalAmount(Long price) {
        return price * (100 - feeRate) / 100;
    }
}
