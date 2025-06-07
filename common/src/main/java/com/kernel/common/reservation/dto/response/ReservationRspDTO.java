package com.kernel.common.reservation.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@Getter
public class ReservationRspDTO {

    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceCategoryId;

    // 우편번호
    private String zipcode;

    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;

    // 위도
    private BigDecimal latitude;

    // 경도
    private BigDecimal longitude;

    // 청소 요청 날짜
    private LocalDate requestDate;

    // 청소 시작 희망 시간
    private LocalTime startTime;

    // 소요 시간
    private Integer turnaround;

    // 예약 금액
    private Integer price;

    // 고객 요청사항 메모
    private String memo;
}
