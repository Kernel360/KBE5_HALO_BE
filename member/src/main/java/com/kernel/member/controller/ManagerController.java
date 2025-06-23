package com.kernel.member.controller;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.service.ManagerService;
import com.kernel.member.service.ManagerTerminationService;
import com.kernel.member.service.request.ManagerSignupReqDTO;
import com.kernel.member.service.request.ManagerTerminationReqDTO;
import com.kernel.member.service.request.ManagerUpdateReqDTO;
import com.kernel.member.service.response.ManagerDetailRspDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/managers/auth")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;
    private final ManagerTerminationService managerTerminationService;

    /**
     * 매니저 회원가입
     * @param signupReqDTO 매니저 회원가입 요청 DTO
     * @return 회원가입 성공 응답
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @RequestBody @Valid ManagerSignupReqDTO signupReqDTO
    ) {
        // 매니저 회원가입 로직
        managerService.signup(signupReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 회원가입 성공", null));
    }

    /**
     * 매니저 정보 조회
     * @param manager 인증된 매니저 정보
     * @return 매니저 정보를 담은 응답
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ManagerDetailRspDTO>> getManager(
            @AuthenticationPrincipal CustomUserDetails manager
    ) {
        ManagerDetailRspDTO infoRspDTO = managerService.getManager(manager.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 조회 성공", infoRspDTO));
    }

    /**
     * 매니저 정보 수정
     * @param manager 인증된 매니저 정보
     * @param updateReqDTO 매니저 정보 수정 요청 DTO
     * @return 수정된 매니저 정보를 담은 응답
     */
    @PatchMapping("/my")
    public ResponseEntity<ApiResponse<ManagerDetailRspDTO>> updateManager(
            @AuthenticationPrincipal CustomUserDetails manager,
            @RequestBody @Valid ManagerUpdateReqDTO updateReqDTO
    ) {
        ManagerDetailRspDTO updateRspDTO = managerService.updateManager(manager.getUserId(), updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 수정 성공", updateRspDTO));
    }

    /**
     * 매니저 계약 해지 요청
     * @param manager 인증된 매니저 정보
     * @param request 계약 해지 요청 DTO
     * @return 계약 해지 요청 성공 응답
     */
    @PostMapping("/termination")
    public ResponseEntity<ApiResponse<Void>> requestTermination(
            @AuthenticationPrincipal CustomUserDetails manager,
            @RequestBody @Valid ManagerTerminationReqDTO request
    ) {
        // 매니저 계약 해지 요청 처리
        managerTerminationService.requestTermination(manager.getUserId(), request);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 계약 해지 요청 성공", null));
    }

}
