package com.kernel.common.reservation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExtraServiceRspDTO {

    // 추가 서비스 이름
    private String extraServiceName;

    // 추가 서비스 가격 //TODO 가격관리 정의되면 추가 CustomCustomerReservationRepositoryImpl
    //private Integer extraServicePrice;

    // 추가 서비스 소요 시간
    private Integer extraServiceTime;

    // 결제 수단 // TODO 가격관리 정의되면 추가

}
