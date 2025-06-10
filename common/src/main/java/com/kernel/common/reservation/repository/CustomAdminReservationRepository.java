package com.kernel.common.reservation.repository;

import com.kernel.common.reservation.dto.response.AdminReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.AdminReservationListRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAdminReservationRepository {

    // 관리자 예약 목록 조회 (상태 필터, 페이징)
    Page<AdminReservationListRspDTO> findReservationList(ReservationStatus status, Pageable pageable);

    // 관리자 예약 상세 조회
    AdminReservationDetailRspDTO findReservationDetail(Long reservationId);

}
