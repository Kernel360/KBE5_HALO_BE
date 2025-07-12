package com.kernel.reservation.service.response.common;


import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Builder
@Getter
public class ReservationMatchedRspDTO {

    // 예약ID
    private ReservationRspDTO reservation;

    // 고객 요청사항 메모
    private String memo;

    // 핸드폰 번호
    private String phone;

    // 요청 서비스 카테고리
    private ServiceCategoryTreeDTO requestCategory;

    // 매칭 매니저 리스트
    private Page<MatchedManagersRspDTO> matchedManagers;
}
