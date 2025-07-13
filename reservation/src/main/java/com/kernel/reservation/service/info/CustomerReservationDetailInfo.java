package com.kernel.reservation.service.info;

import com.kernel.payment.common.enums.PaymentMethod;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationDetailInfo {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceId;

    // 예약 금액
    private Integer price;

    // 예약 상태
    private ReservationStatus status;

    // 고객 요청사항 메모
    private String memo;

    // 핸드폰 번호
    private String phone;

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

    /* Schedule */
    // 서비스 이용 날짜
    private LocalDate requestDate;

    // 시버스 시작 희망 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    /* ExtraService */
    // 추가 서비스
    private List<ExtraServiceInfo> extraServices;

    /* User */
    // 매니저 이름
    private String managerName;

    /* Manager */
    // 매니저 한줄 소개
    private String bio;

    // 매니저 프로필 url
    private String filePathsJson;

    /* ManagerStatistic */
    // 리뷰 수
    private Integer reviewCount;

    // 예약 수
    private Integer reservationCount;

    // 별점 평균
    private BigDecimal averageRating;

    /* ReservationCancel */
    private ReservationCancelInfo reservationCancel;

    /* Payment */
    // 결제 수단
    private PaymentMethod paymentMethod;

    // 결제 금액
    private Integer amount;

    // 결제날짜
    private LocalDateTime paidAt;


    /* Review */
    private ReviewInfo review;

    // 추가서비스 설정
    public void initExtraServiceList(List<ExtraServiceInfo> extraInfo){
        extraServices = extraInfo;
    }

    // 예약 취소 정보 설정
    public void initCancelInfo(ReservationCancelInfo cancelInfo){
        this.reservationCancel = cancelInfo;
    }

    // 리뷰 설정
    public void initReview(ReviewInfo reviewInfo){
        this.review = reviewInfo;
    }


}
