package com.kernel.common.manager.controller;

import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.dto.request.CleaningLogCheckInReqDTO;
import com.kernel.common.manager.dto.request.CleaningLogCheckOutReqDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckInRspDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckOutRspDTO;
import com.kernel.common.manager.service.CleaningLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/managers/reservations")
public class CleaningLogController {

    private final CleaningLogService cleaningLogService;
    
    /**
     * 체크인 API
     * @param manager 매니저
     * @param reservationId 예약ID
     * @param requestDTO 체크인 요청 정보
     * @return 체크인 정보를 담은 응답
     */
    @PostMapping("/{reservation-id}/check-in")
    public ResponseEntity<ApiResponse<CleaningLogCheckInRspDTO>> checkIn(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody CleaningLogCheckInReqDTO requestDTO
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")"
            );
        }
        CleaningLogCheckInRspDTO responseDTO = cleaningLogService.checkIn(manager.getUserId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "체크인 성공", responseDTO));
    }

    /**
     * 체크아웃 API
     * @param reservationId 예약ID
     * @param requestDTO 체크아웃요청DTO
     * @return 체크아웃 정보를 담은 응답
     */
    @PatchMapping("/{reservation-id}/check-out")
    public ResponseEntity<ApiResponse<CleaningLogCheckOutRspDTO>> checkOut(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody CleaningLogCheckOutReqDTO requestDTO
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")"
            );
        }
        CleaningLogCheckOutRspDTO responseDTO = cleaningLogService.checkOut(manager.getUserId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "체크아웃 성공", responseDTO));
    }
}
