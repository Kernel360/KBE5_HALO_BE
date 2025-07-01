package com.kernel.reservation.service.response;

import com.kernel.reservation.service.info.CustomerReservationSummaryInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationSummaryRspDTO {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceCategoryId;

    // 예약 금액
    private Integer price;

    // 예약 상태
    private ReservationStatus reservationStatus;

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
    private String managerName;

    /* ServiceCategory */
    // 서비스 종류(대분류)
    private String serviceName;

    /* Review */
    // 리뷰 id
    private Long reviewId;

    public void updateManagerName(String managerName){
        this.managerName = managerName;
    }

    // CustomerReservationSummaryInfo -> CustomerReservationSummaryRspDTO
    public static CustomerReservationSummaryRspDTO fromInfo(CustomerReservationSummaryInfo info){
        return CustomerReservationSummaryRspDTO.builder()
                .reservationId(info.getReservationId())
                .serviceCategoryId(info.getServiceCategoryId())
                .price(info.getPrice())
                .reservationStatus(info.getStatus())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .managerName(info.getUserName())
                .serviceName(info.getServiceName())
                .reviewId(info.getReviewId())
                .build();
    }

}
