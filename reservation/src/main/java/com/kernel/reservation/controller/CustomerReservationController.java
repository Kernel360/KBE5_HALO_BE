package com.kernel.reservation.controller;


import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.reservation.service.CustomerReservationService;
import com.kernel.reservation.service.MatchService;
import com.kernel.reservation.service.ServiceCategoryService;
import com.kernel.reservation.service.request.*;
import com.kernel.reservation.service.response.CustomerReservationConfirmRspDTO;
import com.kernel.reservation.service.response.CustomerReservationDetailRspDTO;
import com.kernel.reservation.service.response.CustomerReservationSummaryRspDTO;
import com.kernel.reservation.service.response.common.MatchedManagersRspDTO;
import com.kernel.reservation.service.response.common.ReservationMatchedRspDTO;
import com.kernel.reservation.service.response.common.ReservationRspDTO;
import com.kernel.reservation.service.response.common.ServiceCategoryTreeDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers/reservations")
@RequiredArgsConstructor
public class CustomerReservationController {

    private final CustomerReservationService customerReservationService;
    private final ServiceCategoryService serviceCategoryService;
    private final MatchService matchServiceService;

    /**
     * 예약 요청
     * @param user 로그인한 유저
     * @param reservationReqDTO 수요자 예약 요청 DTO
     * @return 예약 요청 내용 + 매칭 매니저 리스트
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ReservationMatchedRspDTO>> makeReservationByCustomer(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReservationReqDTO reservationReqDTO,
            @PageableDefault(size = 5, page = 0, sort = "averageRating", direction = Sort.Direction.DESC) Pageable pageable
    ){

        // 1. 보유 포인트 검사
        customerReservationService.validateSufficientPoints(user.getUserId(), reservationReqDTO.getPrice());

        // 2. 매칭 매니저 조회
        Page<MatchedManagersRspDTO> matchedManagers = matchServiceService.getMatchingManagers(ManagerReqDTO.toManagerReqDTO(reservationReqDTO), user.getUserId(), pageable);

        // 3. 예약 요청 서비스 카테고리 조회
        ServiceCategoryTreeDTO requestCategory
                = serviceCategoryService.getRequestServiceCategory(reservationReqDTO.getMainServiceId(), reservationReqDTO.getAdditionalServiceIds());

        // 4. 예약 요청 저장
        ReservationRspDTO requestedReservation = customerReservationService.makeReservationByCustomer(user.getUserId(), reservationReqDTO);

        // 5. 예약ID + 매칭 매니저 리스트
        ReservationMatchedRspDTO result = ReservationMatchedRspDTO.builder()
                .reservation(requestedReservation)
                .requestCategory(requestCategory)
                .matchedManagers(matchedManagers)
                .build();

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 예약 요청 성공", result));
    }

    /**
     * 매니저 리스트 정렬 및 페이징
     * @param user 로그인한 유저
     * @param managerReqDTO 수요자 매니저 요청DTO
     * @return 매칭 매니저 리스트
     */
    @PostMapping("/managers")
    public ResponseEntity<ApiResponse<Page<MatchedManagersRspDTO>>> getMatchingManagersBySortWithPaging(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ManagerReqDTO managerReqDTO,
            @PageableDefault(size = 5, page = 0, sort = "averageRating", direction = Sort.Direction.DESC) Pageable pageable
    ){

        // 매칭 매니저 조회
        Page<MatchedManagersRspDTO> matchedManagers = matchServiceService.getMatchingManagers(managerReqDTO, user.getUserId(), pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "매칭 매니저 조회 성공", matchedManagers));
    }

    /**
     * 예약 확정
     * @param reservationId 예약ID
     * @param user 로그인한 유저
     * @param confirmReqDTO 예약 확정 DTO
     * @return 확정한 예약 정보
     */
    @PatchMapping("/{reservation-id}/confirm")
    public ResponseEntity<ApiResponse<CustomerReservationConfirmRspDTO>> confirmReservation(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody ReservationConfirmReqDTO confirmReqDTO
    ){

        CustomerReservationConfirmRspDTO rspDTO
                = customerReservationService.confirmReservation(user.getUserId(), reservationId, confirmReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 예약 확정 성공", rspDTO));
    }


    /**
     * 예약 내역 조회
     * @param searchCondDTO 조회조건
     * @param user 로그인한 유저
     * @param pageable 페이징 정보
     * @return 검색 조건에 따른 예약 목록(페이징 포함)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<CustomerReservationSummaryRspDTO>>> getCustomerReservations(
            @ModelAttribute CustomerReservationSearchCondDTO searchCondDTO,
            @PageableDefault(size = 5) Pageable pageable,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        Page<CustomerReservationSummaryRspDTO> rspDTOPage
                = customerReservationService.getCustomerReservations(user.getUserId(), searchCondDTO, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true,  "수요자 예약 내역 조회 성공", rspDTOPage));
    }

    /**
     * 예약 내역 상세 조회
     * @param reservationId 예약 ID
     * @param user 로그인한 유저
     * @return 조회된 예약 상세 정보
     */
    @GetMapping("{reservation-id}")
    public ResponseEntity<ApiResponse<CustomerReservationDetailRspDTO>> getCustomerReservationDetail(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        CustomerReservationDetailRspDTO detailRspDTO
                = customerReservationService.getCustomerReservationDetail(user.getUserId(),reservationId);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 예약 내역 상세 조회 성공", detailRspDTO));
    }

    /**
     * 예약 취소
     * @param user 로그인한 유저
     * @param reservationId 예약 ID
     * @param cancelReqDTO 예약 취소 요청 DTO
     */
    @PatchMapping("/{reservation-id}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancelReservationByCustomer(
            @PathVariable("reservation-id") Long reservationId,
            @RequestBody ReservationCancelReqDTO cancelReqDTO,
            @AuthenticationPrincipal CustomUserDetails user
    ){

        customerReservationService.cancelReservationByCustomer(user.getUserId(), user.getRole(), reservationId, cancelReqDTO);

        return ResponseEntity.ok(new ApiResponse<Void>(true, "수요자 예약 취소 성공", null));
    }

    /**
     * 예약 확정 전 취소
     * @param reservationId 예약ID
     * @param user 수요자ID
     */
    @PatchMapping("/{reservation-id}/pre-cancel")
    public ResponseEntity<ApiResponse<Void>> cancelBeforeConfirmReservation(
            @PathVariable("reservation-id") Long reservationId,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        customerReservationService.cancelBeforeConfirmReservation(user.getUserId(), user.getRole(), reservationId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 예약 확정 전 취소 성공", null));
    }
}
