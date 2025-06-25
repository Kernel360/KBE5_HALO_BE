package com.kernel.reservation.service;

import com.kernel.reservation.repository.ManagerReservationRepository;
import com.kernel.reservation.service.info.ManagerReservationDetailInfo;
import com.kernel.reservation.service.info.ManagerReservationSummaryInfo;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;
import com.kernel.reservation.service.response.ManagerReservationSummaryRspDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ManagerReservationServiceImpl implements ManagerReservationService {

    private final ManagerReservationRepository managerReservationRepository;

    /**
     * 매니저에게 할당된 예약 목록 조회 (검색 조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조건에 맞는 예약 정보를 담은 Page 객체
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ManagerReservationSummaryRspDTO> searchManagerReservationsWithPaging(
            Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable) {
        
        // 조건 및 페이징 포함된 매니저에게 할당된 예약 목록 조회
        Page<ManagerReservationSummaryInfo> searchedReservationPage = managerReservationRepository.searchManagerReservationsWithPaging(managerId, searchCondDTO, pageable);

        // ManagerReservationSummaryInfo -> ManagerReservationSummaryRspDTO 변환
        List<ManagerReservationSummaryRspDTO> dtoList = ManagerReservationSummaryRspDTO.fromInfo(searchedReservationPage);

        return new PageImpl<>(dtoList, pageable, searchedReservationPage.getTotalElements());
    }

    /**
     * 매니저에게 할당된 예약 상세 조회
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @return 매니저 예약 상세 정보 DTO
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerReservationRspDTO getManagerReservation(Long managerId, Long reservationId) {

        // 매니저에게 할당된 예약 상세 조회
        ManagerReservationDetailInfo managerDetail = managerReservationRepository.findByManagerIdAndReservationId(managerId, reservationId);

        ManagerReservationRspDTO responseDTO = ManagerReservationRspDTO.fromInfo(managerDetail);

        return responseDTO;
    }
}
