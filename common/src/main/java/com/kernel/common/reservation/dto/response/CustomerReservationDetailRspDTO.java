package com.kernel.common.reservation.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CustomerReservationDetailRspDTO {

    // 예약 ID
    private Long reservationId;

    // 예약 도로명 주소
    private String roadAddress;

    // 예약 상세주소
    private String detailAddress;

    // 예약 상태
    private ReservationStatus reservationStatus;

    // 이용 날짜
    private LocalDate requestDate;

    // 서비스 시작 희망 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    // 총 결제 금액
    private Integer totalPrice;

    // 서비스 종류(대분류)
    private String serviceName;

    // 추가
    private List<ExtraServiceRspDTO> extraServices;

    // 매니저 이름
    private String managerName;

    // 매니저 한줄 소개
    private String bio;

    // 매니저 평균 별점
    private BigDecimal averageRating;

    // 매니저 리뷰 수
    private Integer reviewCount;

    // 결제 수단 //TODO 가격관리 정의 후 수정
    //private String payment;





}
