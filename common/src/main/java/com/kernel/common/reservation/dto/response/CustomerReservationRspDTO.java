package com.kernel.common.reservation.dto.response;

import com.kernel.common.reservation.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerReservationRspDTO {

    // 예약 ID
    private Long reservationId;

    // 매니저 이름
    private String managerName;

    // 예약 상태
    private ReservationStatus reservationStatus;

    // 서비스ID
    private Long serviceCategoryId;

    // 서비스 종류(대분류)
    private String serviceName;

    // 이용 날짜
    private LocalDate requestDate;

    // 서비스 시작 희망 시간
    private LocalTime startTime;

    // 서비스 소요 시간
    private Integer turnaround;

    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;

    // 추가서비스
    private ServiceCategoryTreeDTO extraServices;

    // 총 결제 금액
    private Integer price;

    public void updateExtraServices(ServiceCategoryTreeDTO categoryTreeDTO){
        this.extraServices = categoryTreeDTO;
    }

    public void updateManagerName(String managerName){
        this.managerName = managerName;
    }

}
