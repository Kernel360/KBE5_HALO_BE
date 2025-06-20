package com.kernel.member.service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReqDTO {

    //포인트
    @Builder.Default
    private Integer point = 0;
}