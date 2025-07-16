package com.kernel.sharedDomain.service.response;

import com.kernel.global.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Reservation ID와 정산 저장용 User 객체를 매핑하기 위한 DTO.
 * 이 User 객체는 정산 저장 시 연관 설정용으로만 사용되며,
 * 화면 출력 또는 필드 접근 용도로 사용하지 않는다.
 */
@Getter
@AllArgsConstructor
public class ReservationManagerMappingInfo {

    private Long reservationId;
    private User manager;

}
