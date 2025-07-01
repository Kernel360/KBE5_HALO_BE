package com.kernel.reservation.service.response;

import com.kernel.reservation.service.info.CustomerReservationDetailInfo;
import com.kernel.reservation.service.response.common.ExtraServiceRspDTO;
import com.kernel.reservation.service.response.common.ReservationCancelRspDTO;
import com.kernel.reservation.service.response.common.ReviewRspDTO;
import com.kernel.reservation.service.response.common.StatisticRspDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationDetailRspDTO {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceCategoryId;

    // 예약 금액
    private Integer price;

    // 예약 상태
    private ReservationStatus reservationStatus;

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
    private List<ExtraServiceRspDTO> extraServices;

    /* User */
    // 매니저 이름
    private String managerName;

    /* Manager */
    // 매니저 한줄 소개
    private String bio;

    /* ManagerStatistic */
    private StatisticRspDTO mangerStatistic;

    /* ReservationCancel */
    private ReservationCancelRspDTO reservationCancel;

    /* Payment */
    // 결제 수단 //TODO 가격관리 정의 후 수정
    //private String payment;

    /* Review */
    private ReviewRspDTO review;

    public static CustomerReservationDetailRspDTO fromInfo(CustomerReservationDetailInfo info){
        return CustomerReservationDetailRspDTO.builder()
                .reservationId(info.getReservationId())
                .serviceCategoryId(info.getServiceCategoryId())
                .price(info.getPrice())
                .reservationStatus(info.getReservationStatus())
                .memo(info.getMemo())
                .phone(info.getPhone())
                .serviceName(info.getServiceName())
                .serviceTime(info.getServiceTime())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .extraServices(ExtraServiceRspDTO.fromInfo(info.getExtraServices()))
                .managerName(info.getManagerName())
                .bio(info.getBio())
                .mangerStatistic(StatisticRspDTO.fromInfo(info))
                .reservationCancel(ReservationCancelRspDTO.fromInfo(info.getReservationCancel()))
                .review(ReviewRspDTO.fromInfo(info.getReview()))
                .build();

    }

}
