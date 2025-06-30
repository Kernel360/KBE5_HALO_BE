package com.kernel.reservation.service;


import com.kernel.reservation.repository.AdminReservationRepository;
import com.kernel.reservation.service.info.AdminReservationDetailInfo;
import com.kernel.reservation.service.info.AdminReservationSummaryInfo;
import com.kernel.reservation.service.request.AdminReservationSearchCondDTO;
import com.kernel.reservation.service.response.AdminReservationDetailRspDTO;
import com.kernel.reservation.service.response.AdminReservationSummaryRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminReservationServiceImpl implements AdminReservationService {

    private final AdminReservationRepository reservationRepository;

    /**
     * 전체 예약 조회(검색 조건 및 페이징 처리)
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저에게 할당된 예약 목록을 응답 (페이징 포함)
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AdminReservationSummaryRspDTO> getReservationList(AdminReservationSearchCondDTO searchCondDTO, Pageable pageable) {

        Page<AdminReservationSummaryInfo> summaryInfo =  reservationRepository.findReservationList(searchCondDTO, pageable);

        return summaryInfo.map(AdminReservationSummaryRspDTO::fromInfo);
    }

    /**
     * 예약 상세 조회
     * @param reservationId 예약ID
     * @return 조회된 예약 상세 정보
     */
    @Override
    @Transactional(readOnly = true)
    public AdminReservationDetailRspDTO getReservationDetail(Long reservationId) {

        AdminReservationDetailInfo foundInfo = reservationRepository.findReservationDetail(reservationId);

        return AdminReservationDetailRspDTO.fromInfo(foundInfo);
    }
}
