package com.kernel.app.controller;

import com.kernel.app.service.ManagerAuthService;
import com.kernel.common.global.AuthenticatedUser;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.global.enums.UserStatus;
import com.kernel.common.manager.dto.request.ManagerSignupReqDTO;
import com.kernel.common.manager.dto.request.ManagerTerminationReqDTO;
import com.kernel.common.manager.dto.request.ManagerUpdateReqDTO;
import com.kernel.common.manager.dto.response.ManagerInfoRspDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/managers/auth")
@RequiredArgsConstructor
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    /**
     * 매니저 회원가입 API
     * @param signupReqDTO 회원가입 요청 DTO
     * @return null
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody ManagerSignupReqDTO signupReqDTO){
        managerAuthService.signup(signupReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 회원가입 성공", null));
    }

    /**
     * 매니저 정보 조회 API
     * @param manager 매니저
     * @return 매니저 정보를 담은 응답
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ManagerInfoRspDTO>> getManager(
        @AuthenticationPrincipal AuthenticatedUser manager
    ) {
        if (   !UserStatus.ACTIVE.equals(manager.getStatus())               // 활성
            && !UserStatus.PENDING.equals(manager.getStatus())              // 대기
            && !UserStatus.REJECTED.equals(manager.getStatus())             // 매니저 승인 거절
            && !UserStatus.TERMINATION_PENDING.equals(manager.getStatus())  // 매니저 계약 해지 대기
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        ManagerInfoRspDTO responseDTO = managerAuthService.getManager(manager.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 조회 성공", responseDTO));
    }

    /**
     * 매니저 정보 수정 API
     * @param manager 매니저
     * @param updateReqDTO 매니저 정보 수정 요청 DTO
     * @return null
     */
    @PatchMapping("/my")
    public ResponseEntity<ApiResponse<Void>> updateManager(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @Valid @RequestBody ManagerUpdateReqDTO updateReqDTO
    ) {
        if (   !UserStatus.ACTIVE.equals(manager.getStatus())   // 활성
            && !UserStatus.REJECTED.equals(manager.getStatus()) // 매니저 승인 거절
        ) {
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        managerAuthService.updateManager(manager.getUserId(), updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 수정 성공", null));
    }

    /**
     * 매니저 계약 해지 요청 API
     * @param manager 매니저
     * @param terminationReqDTO 매니저 계약 해지 요청 DTO
     * @return null
     */
    @PatchMapping("/my/request-termination")
    public ResponseEntity<ApiResponse<Void>> requestTermination(
        @AuthenticationPrincipal AuthenticatedUser manager,
        @Valid @RequestBody ManagerTerminationReqDTO terminationReqDTO
    ) {
        if (!UserStatus.ACTIVE.equals(manager.getStatus())) { // 활성
            throw new AccessDeniedException(
                "죄송합니다. 현재 계정 상태에서는 해당 요청을 처리할 수 없습니다. (상태: " + manager.getStatus().getLabel() + ")" );
        }
        managerAuthService.requestManagerTermination(manager.getUserId(), terminationReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 계약 해지 요청 성공", null));
    }
}
