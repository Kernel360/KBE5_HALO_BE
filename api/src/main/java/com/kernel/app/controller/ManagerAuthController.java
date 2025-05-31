package com.kernel.app.controller;

import com.kernel.app.service.ManagerAuthService;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.manager.dto.request.ManagerSignupReqDTO;
import com.kernel.common.manager.dto.request.ManagerTerminationReqDTO;
import com.kernel.common.manager.dto.request.ManagerUpdateReqDTO;
import com.kernel.common.manager.dto.response.ManagerInfoRspDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
     * @param signupReqDTO // 회원가입 요청 DTO
     * @return null
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(@Valid @RequestBody ManagerSignupReqDTO signupReqDTO){
        managerAuthService.signup(signupReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 회원가입 성공", null));
    }

    /**
     * 매니저 정보 조회 API
     * @return 매니저 정보를 담은 응답
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<ManagerInfoRspDTO>> getManager() {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        ManagerInfoRspDTO responseDTO = managerAuthService.getManager(1L);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 조회 성공", responseDTO));
    }

    /**
     * 매니저 정보 수정 API
     * @param updateReqDTO 매니저 정보 수정 요청 DTO
     * @return null
     */
    @PatchMapping("/my")
    public ResponseEntity<ApiResponse<Void>> updateManager(
        @Valid @RequestBody ManagerUpdateReqDTO updateReqDTO
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        managerAuthService.updateManager(1L, updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 정보 수정 성공", null));
    }

    /**
     * 매니저 계약 해지 요청 API
     * @param terminationReqDTO 매니저 계약 해지 요청 DTO
     * @return null
     */
    @PatchMapping("/my/request-termination")
    public ResponseEntity<ApiResponse<Void>> requestTermination(
        @Valid @RequestBody ManagerTerminationReqDTO terminationReqDTO
    ) {
        // TODO: @AuthenticationPrincipal 사용이 가능해지면 1L이 아닌 실제 id 넘길 예정
        managerAuthService.requestManagerTermination(1L, terminationReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "매니저 계약 해지 요청 성공", null));
    }
}
