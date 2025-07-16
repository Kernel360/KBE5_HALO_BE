package com.kernel.reservation.service.info;

import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminReservationSummaryInfo {

    // 예약 ID
    private Long reservationId;

    // 요청 날짜
    private LocalDate requestDate;

    // 서비스 시작 시간
    private LocalTime startTime;

    // 도로명 주소
    private String roadAddress;

    // 매니저 ID
    private Long managerId;

    // 매니저 이름
    private String managerName;

    // 수요자ID
    private Long customerId;

    // 수요자 이름
    private String customerName;

    // 예약 상태
    private ReservationStatus status;

    // 결제 상태
    private PaymentStatus paymentStatus;

    // 서비스 이름
    private String serviceName;

    // 서비스 가격
    private Integer price;

}
