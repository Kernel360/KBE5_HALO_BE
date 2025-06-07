package com.kernel.common.reservation.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.matching.dto.ManagerMatchingRspDTO;
import com.kernel.common.matching.service.MatchingManagerService;
import com.kernel.common.reservation.dto.request.CustomerReservationCancelReqDTO;
import com.kernel.common.reservation.dto.request.CustomerReservationReqDTO;
import com.kernel.common.reservation.dto.request.ReservationConfirmReqDTO;
import com.kernel.common.reservation.dto.response.*;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.service.CustomerReservationService;
import com.kernel.common.reservation.service.ServiceCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/reservations")
@RequiredArgsConstructor
public class CustomerReservationController {

    private final CustomerReservationService customerReservationService;
    private final MatchingManagerService matchingService;
    private final ServiceCategoryService serviceCategoryService;

    /**
     * 예약 요청
     * @param customerId 수요자ID
     * @param reservationReqDTO 수요자 예약 요청 DTO
     * @return 예약 요청 내용 + 매칭 매니저 리스트
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationMatchedRspDTO>> requestCustomerReservation(
            @RequestParam(required = false) Long customerId, // TODO 테스트용
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
            @Valid @RequestBody CustomerReservationReqDTO reservationReqDTO
    ){
        // 예약 요청 저장
        ReservationRspDTO requestedReservation = customerReservationService.requestCustomerReservation(customerId, reservationReqDTO);

        // 예약 요청 서비스 카테고리 조회
        ServiceCategoryTreeDTO requestCategory = serviceCategoryService.getRequestServiceCategory(reservationReqDTO.getServiceCategoryId());

        // 매칭 매니저 조회 및 매칭된 매니저 상태 변경 ACTIVE -> MATCHING
        List<ManagerMatchingRspDTO> matchedManagers = matchingService.getMatchingManagers(reservationReqDTO);

        // 예약ID + 매칭 매니저 리스트
        ReservationMatchedRspDTO result = ReservationMatchedRspDTO.builder()
                                        .reservation(requestedReservation)
                                        .requestCategory(requestCategory)
                                        .matchedManagers(matchedManagers)
                                        .build();

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 예약 요청 성공", result));
    }

    /**
     * 예약 확정
     * @param reservationId 예약ID
     * @param customerId 수요자ID
     * @param confirmReqDTO 예약확정 요청DTO
     * @return 확정한 예약 정보
     */
    @PatchMapping("/{reservation-id}/confirm")
    public ResponseEntity<ApiResponse<CustomerReservationRspDTO>> confirmCustomerReservation(
            @PathVariable("reservation-id") Long reservationId,
            @RequestParam Long customerId, // TODO 테스트용
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
            @Valid @RequestBody ReservationConfirmReqDTO confirmReqDTO
    ){
        // 예약 확정
        CustomerReservationRspDTO rspDTO = customerReservationService.confirmCustomerReservation(customerId, reservationId, confirmReqDTO);

        // 매니저 매칭 리스트 상태 변경 MATCHING -> ACTIVE
        matchingService.updateMatchingManagersStatus(confirmReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 예약 확정 성공", rspDTO));
    }

    /**
     * 예약 내역 조회
     * @param status 예약 상태
     * @param customerId 수요자ID
     * @param pageable 페이징 정보
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerReservationRspDTO>>> getCustomerReservations(
            @RequestParam(required = false) ReservationStatus status,
            @RequestParam Long customerId, // TODO 테스트용
            @PageableDefault(size = 10) Pageable pageable
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ) {
        Page<CustomerReservationRspDTO> rspDTOPage
                = customerReservationService.getCustomerReservations(customerId, status, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true,  "수요자 예약 내역 조회 성공", rspDTOPage));
    }

    /**
     * 예약 내역 상세 조회
     * @param reservationId 예약 ID
     * @param customerId 수요자ID
     * @return 조회된 예약 상세 정보
     */
    @GetMapping("{reservation_id}")
    public ResponseEntity<ApiResponse<CustomerReservationDetailRspDTO>> getCustomerReservationDetail(
            @PathVariable("reservation_id") Long reservationId,
            @RequestParam Long customerId // TODO 테스트용
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ){
        CustomerReservationDetailRspDTO detailRspDTO
                = customerReservationService.getCustomerReservationDetail(customerId,reservationId);

        return ResponseEntity.ok(new ApiResponse<CustomerReservationDetailRspDTO>(true, "수요자 예약 내역 상세 조회 성공", detailRspDTO));
    }

    /**
     * 예약 취소
     * @param customerId 수요자ID
     * @param cancelReqDTO 예약 취소 요청 DTO
     */
    @PatchMapping("/{reservation_id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelReservationByCustomer(
            @RequestParam Long customerId, // TODO 테스트용
            @RequestBody CustomerReservationCancelReqDTO cancelReqDTO
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ){
        customerReservationService.cancelReservationByCustomer(customerId, cancelReqDTO);
        return ResponseEntity.ok(new ApiResponse<Void>(true, "수요자 예약 취소 성공", null));
    }
}
