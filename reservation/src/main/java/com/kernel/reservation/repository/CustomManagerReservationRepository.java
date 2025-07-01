package com.kernel.reservation.repository;

import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;

import com.querydsl.core.Tuple;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomManagerReservationRepository {

    // 매니저에게 할당된 예약 목록 조회 (검색조건 및 페이징 처리)
    Page<ManagerReservationSummaryInfo> searchManagerReservationsWithPaging(Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable);

    // 매니저에게 할당된 예약 상세 조회(매니저ID와 예약ID로 조회)
    ManagerReservationDetailInfo findByManagerIdAndReservationId(Long managerId, Long reservationId);
}
