package com.kernel.reservation.controller;

import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.reservation.service.AdminReservationService;
import com.kernel.reservation.service.request.AdminReservationSearchCondDTO;
import com.kernel.reservation.service.response.AdminReservationDetailRspDTO;
import com.kernel.reservation.service.response.AdminReservationSummaryRspDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/reservations")
@RequiredArgsConstructor
public class AdminReservationController {

    private final AdminReservationService adminReservationService;

    /**
     * 전체 예약 조회(검색 조건 및 페이징 처리)
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저에게 할당된 예약 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminReservationSummaryRspDTO>>> getReservationList(
            @ModelAttribute AdminReservationSearchCondDTO searchCondDTO,
            @PageableDefault(size = 15) Pageable pageable
    ) {
        Page<AdminReservationSummaryRspDTO> list = adminReservationService.getReservationList(searchCondDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 예약 목록 조회 성공", list));
    }

    /**
     * 예약 상세 조회
     * @param reservationId 예약ID
     * @return 조회된 예약 상세 정보
     */
    @GetMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<AdminReservationDetailRspDTO>> getReservationDetail(
            @PathVariable("reservation-id") Long reservationId
    ) {
        AdminReservationDetailRspDTO detail = adminReservationService.getReservationDetail(reservationId);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 예약 상세 조회 성공", detail));
    }
}