package com.kernel.reservation.service;


import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.request.ReservationCancelReqDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;
import com.kernel.reservation.service.response.ManagerReservationSummaryRspDTO;
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

    /**
     * 매니저가 예약을 수락
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     */
    void acceptReservation(Long managerId, Long reservationId);

    /**
     * 매니저가 예약을 거절
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     */
    void rejectReservation(Long managerId, Long reservationId, ReservationCancelReqDTO request);
}
