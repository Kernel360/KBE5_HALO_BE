package com.kernel.reservation.service.response.common;

import com.kernel.global.common.enums.UserRole;
import com.kernel.reservation.service.info.ReservationCancelInfo;
import com.kernel.sharedDomain.domain.entity.Reservation;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationCancelRspDTO {

    // 예약 취소자 이름
    private String canceledByName;

    // 예약 취소자 타입
    private UserRole canceledByType;

    // 예약 취소 사유
    private String cancelReason;

    // 예약 취소 날짜
    private LocalDateTime cancelDate;

    public static ReservationCancelRspDTO fromInfo(ReservationCancelInfo info) {
        return ReservationCancelRspDTO.builder()
                .cancelReason(info.getCancelReason())
                .cancelDate(info.getCreatedAt())
                .build();
    }

}
