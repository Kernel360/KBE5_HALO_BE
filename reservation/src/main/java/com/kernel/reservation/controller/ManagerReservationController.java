package com.kernel.reservation.controller;

import com.kernel.global.common.enums.UserStatus;
import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.reservation.service.ManagerReservationService;
import com.kernel.reservation.service.request.ManagerReservationSearchCondDTO;
import com.kernel.reservation.service.request.ReservationCancelReqDTO;
import com.kernel.reservation.service.request.ServiceCheckInReqDTO;
import com.kernel.reservation.service.request.ServiceCheckOutReqDTO;
import com.kernel.reservation.service.response.ManagerReservationRspDTO;
import com.kernel.reservation.service.response.ManagerReservationSummaryRspDTO;

import com.kernel.reservation.service.response.ServiceCheckInRspDTO;
import com.kernel.reservation.service.response.ServiceCheckOutRspDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Manager Reservation API", description = "매니저 예약 관리 API")
@RestController
@RequestMapping("/api/managers/reservations")
@RequiredArgsConstructor
public class ManagerReservationController {

    public final ManagerReservationService reservationService;

    /**
     * 매니저에게 할당된 예약 목록 조회 API (검색 조건 및 페이징 처리)
     * @param user 매니저
     * @param searchCondDTO 검색조건DTO
     * @param pageable 페이징
     * @return 검색 조건에 따른 매니저에게 할당된 예약 목록을 응답 (페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<ManagerReservationSummaryRspDTO>>> searchManagerReservations(
            @AuthenticationPrincipal CustomUserDetails user,
            @ModelAttribute ManagerReservationSearchCondDTO searchCondDTO,
            Pageable pageable
    ) {

        Page<ManagerReservationSummaryRspDTO> responseDTOPage
            = reservationService.searchManagerReservationsWithPaging(user.getUserId(), searchCondDTO, pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 예약 목록 조회 성공", responseDTOPage));
    }

    /**
     * 매니저에게 할당된 예약 상세 조회 API
     * @param user 매니저
     * @param reservationId
     * @return 매니저에게 할당된 예약 상세 정보를 담은 응답
     */
    @GetMapping("/{reservation-id}")
    public ResponseEntity<ApiResponse<ManagerReservationRspDTO>> getManagerReservation(
        @AuthenticationPrincipal CustomUserDetails user,
        @PathVariable("reservation-id") Long reservationId
    ) {

        ManagerReservationRspDTO responseDTO = reservationService.getManagerReservation(user.getUserId(), reservationId);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 예약 상세 조회 성공", responseDTO));
    }

    /**
     * 매니저가 예약을 수락 API
     * @param user 매니저
     * @param reservationId 예약ID
     */
    @PostMapping("/{reservation-id}/accept")
    public ResponseEntity<ApiResponse<Void>> acceptReservation(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("reservation-id") Long reservationId
    ) {
        reservationService.acceptReservation(user.getUserId(), reservationId);

        return ResponseEntity.ok(new ApiResponse<>(true, "예약 수락 성공", null));
    }

    /**
     * 매니저가 예약을 거절 API
     * @param user 매니저
     * @param reservationId 예약ID
     * @return 예약 거절 성공 응답
     */
    @PostMapping("/{reservation-id}/reject")
    public ResponseEntity<ApiResponse<Void>> rejectReservation(
            @AuthenticationPrincipal CustomUserDetails user,
            @PathVariable("reservation-id") Long reservationId,
            @RequestBody(required = true) ReservationCancelReqDTO request

    ) {
        reservationService.rejectReservation(user.getUserId(), reservationId, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "예약 거절 성공", null));
    }

    /**
     * 체크인 API
     * @param manager 매니저
     * @param reservationId 예약ID
     * @param request 체크인 요청 정보
     * @return 체크인 정보를 담은 응답
     */
    @PostMapping("/{reservation-id}/check-in")
    public ResponseEntity<ApiResponse<ServiceCheckInRspDTO>> checkIn(
            @AuthenticationPrincipal CustomUserDetails manager,
            @PathVariable("reservation-id") Long reservationId,
            @Valid @RequestBody ServiceCheckInReqDTO request
    ) {

        ServiceCheckInRspDTO responseDTO = reservationService.checkIn(manager.getUserId(), reservationId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "체크인 성공", responseDTO));
    }

    /**
     * 체크아웃 API
     * @param reservationId 예약ID
     * @param request 체크아웃요청DTO
     * @return 체크아웃 정보를 담은 응답
     */
    @PatchMapping("/{reservation-id}/check-out")
    public ResponseEntity<ApiResponse<ServiceCheckOutRspDTO>> checkOut(
            @AuthenticationPrincipal CustomUserDetails manager,
            @PathVariable("reservation-id") Long reservationId,
            @Valid @RequestBody ServiceCheckOutReqDTO request
    ) {

        ServiceCheckOutRspDTO responseDTO = reservationService.checkOut(manager.getUserId(), reservationId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "체크아웃 성공", responseDTO));
    }


}
