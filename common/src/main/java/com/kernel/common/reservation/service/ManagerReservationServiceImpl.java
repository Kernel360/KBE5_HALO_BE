package com.kernel.common.reservation.service;

import com.kernel.common.reservation.dto.request.ManagerReservationSearchCondDTO;
import com.kernel.common.reservation.dto.response.ManagerReservationRspDTO;
import com.kernel.common.reservation.dto.response.ManagerReservationSummaryRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.repository.CustomManagerReservationRepository;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagerReservationServiceImpl implements ManagerReservationService {

    private final CustomManagerReservationRepository customManagerReservationRepository;

    /**
     * 매니저에게 할당된 예약 목록 조회 (검색조건 및 페이징 처리)
     * @param managerId 매니저ID
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 조회된 매니저에게 할당된 예약 목록을 담은 응답
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ManagerReservationSummaryRspDTO> searchManagerReservationsWithPaging(
        Long managerId, ManagerReservationSearchCondDTO searchCondDTO, Pageable pageable) {
        
        // 조건 및 페이징 포함된 매니저에게 할당된 예약 목록 조회
        Page<Tuple> searchedReservationPage = customManagerReservationRepository.searchManagerReservationsWithPaging(managerId, searchCondDTO, pageable);

        // Tuple -> SummaryResponseDTO 변환
        List<ManagerReservationSummaryRspDTO> dtoList = searchedReservationPage.getContent().stream()
            .map(tuple -> {
                ReservationStatus status = tuple.get(
                    Expressions.enumPath(ReservationStatus.class, "status"));

                return ManagerReservationSummaryRspDTO.builder()
                    .reservationId(tuple.get(Expressions.numberPath(Long.class, "reservationId")))
                    .requestDate(tuple.get(Expressions.datePath(LocalDate.class, "requestDate")))
                    .startTime(tuple.get(Expressions.timePath(LocalTime.class, "startTime")))
                    .turnaround(tuple.get(Expressions.numberPath(Integer.class, "turnaround")))
                    .customerName(tuple.get(Expressions.stringPath("customerName")))
                    .customerAddress(tuple.get(Expressions.stringPath("customerAddress")))
                    .serviceName(tuple.get(Expressions.stringPath("serviceName")))
                    .status(status)
                    .statusName(status != null ? status.getLabel() : null)
                    .checkId(tuple.get(Expressions.numberPath(Long.class, "checkId")))
                    .isCheckedIn(tuple.get(Expressions.booleanPath("isCheckedIn")))
                    .inTime(tuple.get(Expressions.dateTimePath(LocalDateTime.class, "inTime")))
                    .isCheckedOut(tuple.get(Expressions.booleanPath("isCheckedOut")))
                    .outTime(tuple.get(Expressions.dateTimePath(LocalDateTime.class, "outTime")))
                    .managerReviewId(tuple.get(Expressions.numberPath(Long.class, "managerReviewId")))
                    .isReviewed(tuple.get(Expressions.booleanPath("isReviewed")))
                    .build();
            })
            .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, searchedReservationPage.getTotalElements());
    }

    /**
     * 매니저에게 할당된 예약 상세 조회
     * @param managerId 매니저ID
     * @param reservationId 예약ID
     * @return 매니저에게 할당된 예약 상세 정보를 담은 응답
     */
    @Override
    @Transactional(readOnly = true)
    public ManagerReservationRspDTO getManagerReservation(Long managerId, Long reservationId) {

        // 매니저에게 할당된 예약 상세 조회
        ManagerReservationRspDTO responseDTO = customManagerReservationRepository.findByManagerIdAndReservationId(managerId, reservationId);

        // 상태명(statusName) 추가 처리
        if (responseDTO != null && responseDTO.getStatus() != null) {
            responseDTO.setStatusName(responseDTO.getStatus().getLabel());
        }

        return responseDTO;
    }
}
