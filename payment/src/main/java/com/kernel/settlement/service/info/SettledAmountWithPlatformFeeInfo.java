package com.kernel.settlement.service.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SettledAmountWithPlatformFeeInfo {

    private Long totalAmount;
    private Long platformFee;


}
