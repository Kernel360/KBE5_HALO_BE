package com.kernel.common.reservation.dto.response;

import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ReservationMatchedRspDTO {

    // 예약ID
    private ReservationRspDTO reservation;

    // 요청 서비스 카테고리
    private ServiceCategoryTreeDTO requestCategory;

    // 매칭 매니저 리스트
    private List<ManagerMatchingRspDTO> matchedManagers;
}
