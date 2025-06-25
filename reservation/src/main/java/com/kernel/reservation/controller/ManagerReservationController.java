package com.kernel.reservation.controller;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.reservation.service.ManagerReservationService;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;
import com.kernel.reservation.service.response.ManagerReservationSummaryRspDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
     * @param manager 매니저
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저에게 할당된 예약 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerReservationSummaryRspDTO>>> searchManagerReservations(
            @AuthenticationPrincipal CustomUserDetails manager,
            @ModelAttribute ManagerReservationSearchCondDTO searchCondDTO,
            Pageable pageable
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus() + ")"
            );
        }
        Page<ManagerReservationSummaryRspDTO> responseDTOPage
            = reservationService.searchManagerReservationsWithPaging(manager.getUserId(), searchCondDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 예약 목록 조회 성공", responseDTOPage));
    }

    /**
     * 매니저에게 할당된 예약 상세 조회 API
     * @param manager 매니저
     * @param reservationId
     * @return 매니저에게 할당된 예약 상세 정보를 담은 응답
     */
    @GetMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ManagerReservationRspDTO>> getManagerReservation(
        @AuthenticationPrincipal CustomUserDetails manager,
        @PathVariable("reservation-id") Long reservationId
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus() + ")"
            );
        }
        ManagerReservationRspDTO responseDTO = reservationService.getManagerReservation(manager.getUserId(), reservationId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 예약 상세 조회 성공", responseDTO));
    }
}
