package com.kernel.common.reservation.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationRspDTO {

    // 예약 ID
    private Long reservationId;

    // 매니저 이름
    private String managerName;

    // 예약 상태
    private ReservationStatus reservationStatus;

    // 서비스 종류(대분류)
    private String serviceName;

    // 이용 날짜
    private LocalDate requestDate;

    // 서비스 시작 희망 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    // 총 결제 금액
    private Integer price;

}
