package com.kernel.reservation.service.request;


import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class AdminReservationSearchCondDTO {

    // 예약 날짜 시작일
    public LocalDate fromRequestDate;

    // 예약 날짜 종료일
    public LocalDate toRequestDate;

    // 예약 상태
    private List<ReservationStatus> reservationStatus;

    // 결제 상태
    private List<PaymentStatus> paymentStatus;

    // 체크인 여부
    private Boolean isCheckedIn;

    // 체크아웃 여부
    private Boolean isCheckedOut;

    // 리뷰 작성 여부
    private Boolean isReviewed;

    // 고객명
    public String customerNameKeyword;

    // 매니저 명
    public String managerNameKeyword;
}
