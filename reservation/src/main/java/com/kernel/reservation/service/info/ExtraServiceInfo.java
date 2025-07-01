package com.kernel.reservation.service.info;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ExtraServiceInfo {

    // 추가 서비스 ID
    private Long serviceId;

    // 추가 서비스 이름
    private String serviceName;

    // 추가 서비스 가격
    private Integer price;

    // 추가 서비스 소요 시간
    private Integer serviceTime;

    // 결제 수단 // TODO 가격관리 정의되면 추가

}
