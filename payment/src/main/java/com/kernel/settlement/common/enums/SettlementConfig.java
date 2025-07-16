package com.kernel.settlement.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SettlementConfig {
    
    FEE_RATE(30);
    
    private final int value;
}
