package com.kernel.reservation.service.response.common;

import com.kernel.global.common.enums.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReservationCancelRspDTO {

    // 예약 취소자 id
    private Long canceledById;

    // 예약 취소자 이름
    private String canceledByName;

    // 예약 취소자 타입
    private UserRole canceledByType;

    // 예약 취소 사유
    private String cancelReason;

    // 예약 취소 날짜
    private LocalDateTime cancelDate;

}
