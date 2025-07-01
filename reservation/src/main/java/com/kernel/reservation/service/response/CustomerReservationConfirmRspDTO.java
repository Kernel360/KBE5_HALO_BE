package com.kernel.reservation.service.response;

import com.kernel.payment.common.enums.PaymentMethod;
import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import com.kernel.sharedDomain.common.enums.ReservationStatus;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerReservationConfirmRspDTO {

    /* Reservation */
    // 예약 ID
    private Long reservationId;

    // 서비스 ID
    //private Long serviceCategoryId;

    /* Schedule */
    // 서비스 이용 날짜
    //private LocalDate requestDate;

    // 시버스 시작 희망 시간
   // private LocalTime startTime;

    // 서비스 소요 시간
  //  private Integer turnaround;

    /* Location */
    // 도로명 주소
 //   private String roadAddress;

    // 상세 주소
  //  private String detailAddress;

    /* User */
    // 매니저 이름
  //  private String managerName;

    /* ServiceCategory */
    // 서비스 종류(대분류)
  //  private String serviceName;

    /* extraService*/
 //   private ServiceCategoryTreeDTO serviceCategoryTreeDTO;

    /* payment */
   // private PaymentMethod paymentMethod;

   // private Integer price;

}
