package com.kernel.common.manager.service;

import com.kernel.common.manager.dto.request.ManagerReservationSearchCondDTO;
import com.kernel.common.manager.dto.response.ManagerReservationRspDTO;
import com.kernel.common.manager.dto.response.ManagerReservationSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ManagerReservationService {

    /**
     * 매니저에게 할당된 예약 목록 조회 (검색조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조회된 매니저에게 할당된 예약 목록을 담은 응답
     */
    Page<ManagerReservationSummaryRspDTO> searchManagerReservationsWithPaging(Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable);

    /**
     * 매니저에게 할당된 예약 상세 조회
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @return 매니저에게 할당된 예약 상세 정보를 담은 응답
     */
    ManagerReservationRspDTO getManagerReservation(Long managerId, Long reservationId);
}
