package com.kernel.common.reservation.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.reservation.dto.request.ManagerReservationSearchCondDTO;
import com.kernel.common.reservation.dto.response.ManagerReservationRspDTO;
import com.kernel.common.reservation.dto.response.ManagerReservationSummaryRspDTO;
import com.kernel.common.reservation.service.ManagerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers/reservations")
@RequiredArgsConstructor
public class ManagerReservationController {

    public final ManagerReservationService reservationService;

    /**
     * 매니저에게 할당된 예약 목록 조회 API (검색 조건 및 페이징 처리)
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저에게 할당된 예약 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerReservationSummaryRspDTO>>> searchManagerReservations(
        @ModelAttribute ManagerReservationSearchCondDTO searchCondDTO,
        Pageable pageable
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        Page<ManagerReservationSummaryRspDTO> responseDTOPage
            = reservationService.searchManagerReservationsWithPaging(1L, searchCondDTO, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 예약 목록 조회 성공", responseDTOPage));
    }

    /**
     * 매니저에게 할당된 예약 상세 조회 API
     * @param reservationId
     * @return 매니저에게 할당된 예약 상세 정보를 담은 응답
     */
    @GetMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<ManagerReservationRspDTO>> getManagerReservation(
        @PathVariable("reservation_id") Long reservationId
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        ManagerReservationRspDTO responseDTO = reservationService.getManagerReservation(1L, reservationId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 예약 상세 조회 성공", responseDTO));
    }
}
