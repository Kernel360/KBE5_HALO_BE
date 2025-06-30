package com.kernel.reservation.service.response.common;

import com.kernel.reservation.domain.entity.ReservationLocation;
import com.kernel.reservation.domain.entity.ReservationSchedule;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import com.kernel.sharedDomain.domain.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class ReservationRspDTO {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceCategoryId;

    // 예약 금액
    private Integer price;

    // 예약 상태
    private ReservationStatus reservationStatus;

    /* ServiceCategory */
    // 서비스 종류(대분류)
    private String serviceName;

    // 서비스 대분류 시간
    private Integer serviceTime;

    /* Location */
    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;

    // 위도
    private BigDecimal latitude;

    // 경도
    private BigDecimal longitude;

    /* Schedule */
    // 서비스 이용 날짜
    private LocalDate requestDate;

    // 시버스 시작 희망 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    // Entities -> ReservationRspDTO
    public static ReservationRspDTO fromEntities(
            Reservation reservation,
            ReservationLocation location,
            ReservationSchedule schedule
    ) {
        return ReservationRspDTO.builder()
                .reservationId(reservation.getReservationId())
                .serviceCategoryId(reservation.getServiceCategory().getServiceId())
                .serviceName(reservation.getServiceCategory().getServiceName())
                .serviceTime(reservation.getServiceCategory().getServiceTime())
                .price(reservation.getPrice())
                .roadAddress(location.getRoadAddress())
                .detailAddress(location.getDetailAddress())
                .latitude(location.getLatitude())
                .longitude(location.getLongitude())
                .requestDate(schedule.getRequestDate())
                .startTime(schedule.getStartTime())
                .turnaround(schedule.getTurnaround())
                .build();
    }
}
