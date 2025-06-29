package com.kernel.reservation.service.info;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AdminReservationDetailInfo {

    // 예약 ID
    private Long reservationId;

    // 서비스 요청 날짜
    private LocalDate requestDate;

    // 시작시간
    private LocalTime startTime;

    // 도로명주소
    private String roadAddress;

    // 상세주소
    private String detailAddress;

    // 매니저 이름
    private String managerName;

    // 매니저 폰번호
    private String managerPhone;

    //수요자 이름
    private String customerName;

    // 수요자 폰번호
    private String customerPhone;

    // 예약 상태
    private ReservationStatus status;

    // 서비스 이름
    private String serviceName;

    // 가격
    private Integer price;

    // 메모
    private String memo;
}
