package com.kernel.common.reservation.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.reservation.dto.response.*;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.service.AdminReservationService;
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

    // 전체 예약 목록 조회 (상태 필터링 가능)
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminReservationListRspDTO>>> getReservationList(
            @RequestParam(required = false) ReservationStatus status,
            @PageableDefault(size = 20) Pageable pageable
    ) {
        Page<AdminReservationListRspDTO> list = adminReservationService.getReservationList(status, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 예약 목록 조회 성공", list));
    }

    // 예약 상세 조회

    @GetMapping("/{reservation_id}")
    public ResponseEntity<ApiResponse<AdminReservationDetailRspDTO>> getReservationDetail(
            @PathVariable("reservation_id") Long reservationId
    ) {
        AdminReservationDetailRspDTO detail = adminReservationService.getReservationDetail(reservationId);
        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 예약 상세 조회 성공", detail));
    }
}