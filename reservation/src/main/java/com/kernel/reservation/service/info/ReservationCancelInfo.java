package com.kernel.reservation.service.info;

import com.kernel.global.common.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationCancelInfo {

    // 예약 취소 사유
    private String cancelReason;

    // 예약 취소 날짜
    private LocalDateTime createdAt;

}
