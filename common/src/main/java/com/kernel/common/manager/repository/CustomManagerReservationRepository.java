package com.kernel.common.manager.repository;

import com.kernel.common.manager.dto.request.ManagerReservationSearchCondDTO;
import com.kernel.common.manager.dto.response.ManagerReservationRspDTO;
import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerReservationRepository {

    // 매니저에게 할당된 예약 목록 조회 (검색조건 및 페이징 처리)
    Page<Tuple> searchManagerReservationsWithPaging(Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable);

    // 매니저에게 할당된 예약 상세 조회(매니저ID와 예약ID로 조회)
    ManagerReservationRspDTO findByManagerIdAndReservationId(Long managerId, Long reservationId);
}
