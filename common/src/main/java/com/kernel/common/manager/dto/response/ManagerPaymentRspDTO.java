package com.kernel.common.manager.dto.response;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ManagerPaymentRspDTO {

    // 예약Id
    private Long reservationId;

    // 고객명
    private String customerName;

    // 청소 요청 날짜
    private LocalDate requestDate;

    // 청소 요청 시간(시작시간 ~ 종료시간)
    private String reservationTime;

    // 총 소요 시간
    private Integer turnaround;

    // 서비스명
    private String serviceName;

    // 서비스 금액
    private Integer price;

    // 추가 서비스 금액
    private Integer extraPrice;

    // 총 금액
    private Integer totalPrice;

    // 수수료
    private Integer commission;

    // 정산 금액
    private Integer settlementAmount;

    // 추가 서비스 내역
    private List<ExtraServiceDTO> extraServices;

    @Getter
    @SuperBuilder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExtraServiceDTO {
        private Long extraServiceId;
        private String extraServiceName;
        private Integer extraPrice;
    }
}
