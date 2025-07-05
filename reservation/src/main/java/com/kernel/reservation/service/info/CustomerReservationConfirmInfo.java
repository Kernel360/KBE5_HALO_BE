package com.kernel.reservation.service.info;

import com.kernel.payment.common.enums.PaymentMethod;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationConfirmInfo {

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
    List<ExtraServiceInfo> extraServiceList;

    /* payment */
    private PaymentMethod paymentMethod;

    private Integer amount;

    // 추가서비스 설정
    public void initExtraService(List<ExtraServiceInfo> extraServiceList){
        this.extraServiceList = extraServiceList;
    }

}
