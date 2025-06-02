package com.kernel.common.manager.dto.response;

import com.kernel.common.global.enums.ReservationStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
public class ManagerReservationSummaryRspDTO {

    // 예약ID
    private Long reservationId;

    // 청소 요청 날짜
    private LocalDate requestDate;

    // 고객명
    private String customerName;

    // 고객 주소
    private String customerAddress;

    // 서비스명
    private String serviceName;

    // 상태
    private ReservationStatus status;

    // 상태명
    private String statusName;

    // 체크ID
    private Long checkId;

    // 체크인 여부
    private Boolean isCheckedIn;
    
    // 체크인 일시
    private LocalDateTime inTime;

    // 체크아웃 여부
    private Boolean isCheckedOut;
    
    // 체크아웃 일시
    private LocalDateTime outTime;
    
    // 매니저 리뷰ID
    private Long managerReviewId;

    // 매니저 리뷰 여부
    private Boolean isReviewed;
}
