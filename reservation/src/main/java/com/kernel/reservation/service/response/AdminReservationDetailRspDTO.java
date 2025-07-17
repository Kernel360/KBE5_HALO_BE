package com.kernel.reservation.service.response;

import com.kernel.payment.common.enums.PaymentMethod;
import com.kernel.payment.common.enums.PaymentStatus;
import com.kernel.reservation.service.info.AdminReservationDetailInfo;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminReservationDetailRspDTO {

    // 예약 ID
    private Long reservationId;

    // 서비스 요청 날짜
    private LocalDate requestDate;

    // 시작시간
    private LocalTime startTime;

    // 도로명주소
    private String roadAddress;

    // 상세주소
    private String detailAddress;

    // 매니저 ID
    private Long managerId;

    // 매니저 이름
    private String managerName;

    // 매니저 폰번호
    private String managerPhone;

    // 수요자ID
    private Long customerId;

    // 수요자 이름
    private String customerName;

    // 수요자 폰번호
    private String customerPhone;

    // 예약 상태
    private ReservationStatus reservationStatus;

    // 서비스 이름
    private String serviceName;

    // 가격
    private Integer price;

    // 메모
    private String memo;

    // 결제 가격
    private Integer amount;

    // 결제 수단
    private PaymentMethod paymentMethod;

    // 결제 상태
    private PaymentStatus paymentStatus;

    // 결제 날짜
    private LocalDateTime paidAt;

    // AdminReservationDetailInfo -> AdminReservationDetailRspDTO
    public static AdminReservationDetailRspDTO fromInfo(AdminReservationDetailInfo info) {
        return AdminReservationDetailRspDTO.builder()
                .reservationId(info.getReservationId())
                .requestDate(info.getRequestDate())
                .startTime(info.getStartTime())
                .roadAddress(info.getRoadAddress())
                .detailAddress(info.getDetailAddress())
                .managerId(info.getManagerId())
                .managerName(info.getManagerName())
                .managerPhone(info.getManagerPhone())
                .customerId(info.getCustomerId())
                .customerName(info.getCustomerName())
                .customerPhone(info.getCustomerPhone())
                .reservationStatus(info.getReservationStatus())
                .serviceName(info.getServiceName())
                .price(info.getPrice())
                .memo(info.getMemo())
                .amount(info.getAmount())
                .paymentMethod(info.getPaymentMethod())
                .paymentStatus(info.getPaymentStatus())
                .paidAt(info.getPaidAt())
                .build();
    }

}
