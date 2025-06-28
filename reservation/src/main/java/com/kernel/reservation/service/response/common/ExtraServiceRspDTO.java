package com.kernel.reservation.service.response.common;

import com.kernel.reservation.service.info.ExtraServiceInfo;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExtraServiceRspDTO {

    // 추가 서비스 ID
    private Long extraServiceId;

    // 추가 서비스 이름
    private String extraServiceName;

    // 추가 서비스 가격
    private Integer extraServicePrice;

    // 추가 서비스 소요 시간
    private Integer extraServiceTime;

    // 결제 수단 // TODO 가격관리 정의되면 추가

    public static List<ExtraServiceRspDTO> fromInfo(List<ExtraServiceInfo> infoList){
        return infoList.stream()
                .map(info -> ExtraServiceRspDTO.builder()
                        .extraServiceId(info.getServiceId())
                        .extraServiceName(info.getServiceName())
                        .extraServicePrice(info.getPrice())
                        .extraServiceTime(info.getServiceTime())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
