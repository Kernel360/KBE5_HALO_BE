package com.kernel.common.reservation.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.reservation.dto.request.CustomerReservationCancelReqDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationDetailRspDTO;
import com.kernel.common.reservation.dto.response.CustomerReservationRspDTO;
import com.kernel.common.reservation.enums.ReservationStatus;
import com.kernel.common.reservation.service.CustomerReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/reservations")
@RequiredArgsConstructor
public class CustomerReservationController {

    private final CustomerReservationService customerReservationService;

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
            @PageableDefault(size = 3 ) Pageable pageable //TODO size 수정
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
    @PatchMapping("{reservation_id}")
    public ResponseEntity<ApiResponse<Void>> cancelReservationByCustomer(
            @RequestParam Long customerId, // TODO 테스트용
            @RequestBody CustomerReservationCancelReqDTO cancelReqDTO
            // TODO @AuthenticationPrincipal AuthenticatedUser customer
    ){
        customerReservationService.cancelReservationByCustomer(customerId, cancelReqDTO);
        return ResponseEntity.ok(new ApiResponse<Void>(true, "수요자 예약 취소 성공", null));
    }
}
