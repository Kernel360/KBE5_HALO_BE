package com.kernel.reservation.service.response;

import com.kernel.payment.common.enums.PaymentMethod;
import com.kernel.reservation.service.info.CustomerReservationConfirmInfo;
import com.kernel.reservation.service.response.common.ExtraServiceRspDTO;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationConfirmRspDTO {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    private Long serviceCategoryId;

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

    /* Manager */
    private String profileImagePath;

    /* ServiceCategory */
    // 서비스 종류(대분류)
    private String serviceName;

    /* extraService*/
    private List<ExtraServiceRspDTO> extraServiceList;

    /* payment */
    private PaymentMethod paymentMethod;

    private Integer price;

    public static CustomerReservationConfirmRspDTO fromInfo(CustomerReservationConfirmInfo info){
        return CustomerReservationConfirmRspDTO.builder()
                .reservationId(info.getReservationId())
                .serviceCategoryId(info.getServiceCategoryId())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .turnaround(info.getTurnaround())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .managerName(info.getManagerName())
                .profileImagePath(info.getProfileImagePath())
                .serviceName(info.getServiceName())
                .extraServiceList(info.getExtraServiceList() != null ? 
                    ExtraServiceRspDTO.fromInfo(info.getExtraServiceList()) : null)
                .paymentMethod(info.getPaymentMethod())
                .price(info.getAmount())
                .build();
    }

}
