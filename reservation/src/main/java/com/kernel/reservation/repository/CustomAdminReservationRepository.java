package com.kernel.reservation.repository;

import com.kernel.reservation.service.info.AdminReservationDetailInfo;
import com.kernel.reservation.service.info.AdminReservationSummaryInfo;
import com.kernel.reservation.service.request.AdminReservationSearchCondDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomAdminReservationRepository {

    // 예약 목록 조회
    Page<AdminReservationSummaryInfo> findReservationList(AdminReservationSearchCondDTO searchCondDTO, Pageable pageable);

    // 예약 상세 조회
    AdminReservationDetailInfo findReservationDetail(Long reservationId);
}
