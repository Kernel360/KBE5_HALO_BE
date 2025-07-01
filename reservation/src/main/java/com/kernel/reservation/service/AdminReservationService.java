package com.kernel.reservation.service;


import com.kernel.reservation.service.request.AdminReservationSearchCondDTO;
import com.kernel.reservation.service.response.AdminReservationDetailRspDTO;
import com.kernel.reservation.service.response.AdminReservationSummaryRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminReservationService {

    //관리자 예약 목록 조회 (상태 필터링, 페이징)
    Page<AdminReservationSummaryRspDTO> getReservationList(AdminReservationSearchCondDTO searchCondDTO, Pageable pageable);

    // 관리자 예약 상세 조회
    AdminReservationDetailRspDTO getReservationDetail(Long reservationId);
}
