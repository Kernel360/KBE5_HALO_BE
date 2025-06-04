package com.kernel.common.manager.controller;

import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.security.ManagerUserDetails;
import com.kernel.common.manager.dto.request.CleaningLogCheckInReqDTO;
import com.kernel.common.manager.dto.request.CleaningLogCheckOutReqDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckInRspDTO;
import com.kernel.common.manager.dto.response.CleaningLogCheckOutRspDTO;
import com.kernel.common.manager.service.CleaningLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers/reservations")
@RequiredArgsConstructor
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
        @AuthenticationPrincipal ManagerUserDetails manager,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody CleaningLogCheckInReqDTO requestDTO
    ) {
        CleaningLogCheckInRspDTO responseDTO = cleaningLogService.checkIn(manager.getManagerId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "체크인 성공", responseDTO));
    }

    /**
     * 체크아웃 API
     * @param reservationId
     * @param requestDTO
     * @return 체크아웃 정보를 담은 응답
     */
    /**
     * 체크아웃
     * @param manager 매니저
     * @param reservationId 예약ID
     * @param requestDTO 체크아웃 요청 정보
     * @return
     */
    @PostMapping("/{reservation-id}/check-out")
    public ResponseEntity<ApiResponse<CleaningLogCheckOutRspDTO>> checkOut(
        @AuthenticationPrincipal ManagerUserDetails manager,
        @PathVariable("reservation-id") Long reservationId,
        @Valid @RequestBody CleaningLogCheckOutReqDTO requestDTO
    ) {
        CleaningLogCheckOutRspDTO responseDTO = cleaningLogService.checkOut(manager.getManagerId(), reservationId, requestDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "체크아웃 성공", responseDTO));
    }
}
