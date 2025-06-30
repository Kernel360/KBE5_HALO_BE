package com.kernel.reservation.service.info;

import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerReservationSummaryInfo {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceCategoryId;

    // 예약 금액
    private Integer price;

    // 예약 상태
    private ReservationStatus status;

    /* Schedule */
    // 서비스 이용 날짜
    private LocalDate requestDate;

    // 시버스 시작 희망 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    /* Location */
    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;

    /* User */
    // 매니저 이름
    private String userName;

    /* ExtraService */
    // 추가서비스
    private ServiceCategoryTreeDTO extraServices;

    /* ServiceCategory */
    // 서비스 종류(대분류)
    private String serviceName;

    /* Review */
    // 리뷰 id
    private Long reviewId;
}
