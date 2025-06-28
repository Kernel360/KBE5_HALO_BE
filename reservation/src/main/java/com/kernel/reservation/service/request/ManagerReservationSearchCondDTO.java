package com.kernel.reservation.service.request;


import java.time.LocalDate;
import java.util.List;

import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerReservationSearchCondDTO {

    // 예약 날짜 시작일
    public LocalDate fromRequestDate;

    // 예약 날짜 종료일
    public LocalDate toRequestDate;

    // 예약 상태
    private List<ReservationStatus> reservationStatus;

    // 체크인 여부
    private Boolean isCheckedIn;

    // 체크아웃 여부
    private Boolean isCheckedOut;

    // 리뷰 작성 여부
    private Boolean isReviewed;

    // 고객명
    public String customerNameKeyword;
}
