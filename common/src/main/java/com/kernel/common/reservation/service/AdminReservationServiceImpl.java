package com.kernel.common.reservation.service;

import com.kernel.common.reservation.dto.mapper.AdminReservationMapper;
import com.kernel.common.reservation.dto.response.AdminReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.AdminReservationListRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReservationServiceImpl implements AdminReservationService {

    private final ReservationRepository reservationRepository;
    private final AdminReservationMapper adminReservationMapper;

    // 관리자 예약 목록 조회 (상태 필터링, 페이징)
    @Override
    @Transactional(readOnly = true)
    public Page<AdminReservationListRspDTO> getReservationList(ReservationStatus status, Pageable pageable) {

        return reservationRepository.findReservationList(status, pageable);
    }

    // 관리자 예약 상세 조회
    @Override
    @Transactional(readOnly = true)
    public AdminReservationDetailRspDTO getReservationDetail(Long reservationId) {

        return reservationRepository.findReservationDetail(reservationId);
    }
}
