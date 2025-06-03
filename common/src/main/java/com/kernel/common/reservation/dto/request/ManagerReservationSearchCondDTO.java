package com.kernel.common.reservation.dto.request;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ManagerReservationSearchCondDTO {

    // 예약 날짜 시작일
    public LocalDate fromRequestDate;

    // 예약 날짜 종료일
    public LocalDate toRequestDate;

    // 예약 상태
    public ReservationStatus reservationStatus;

    // 체크인 여부
    private Boolean isCheckedIn;

    // 체크아웃 여부
    private Boolean isCheckedOut;

    // 리뷰 작성 여부
    private Boolean isReviewed;

    // 고객명
    public String customerNameKeyword;
}
