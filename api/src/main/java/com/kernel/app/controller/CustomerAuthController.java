package com.kernel.app.controller;


import com.kernel.app.service.CustomerAuthService;
import com.kernel.common.customer.dto.request.CustomerInfoUpdateReqDTO;
import com.kernel.common.customer.dto.request.CustomerPasswordResetReqDTO;
import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;
import com.kernel.common.customer.dto.response.CustomerInfoDetailRspDTO;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/customers/auth")
@RequiredArgsConstructor
public class CustomerAuthController {

    private final CustomerAuthService customerAuthService;

    /**
     * 수요자 회원가입
     * @param signupReqDTO 회원가입 요청 DTO
     * @return null
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody CustomerSignupReqDTO signupReqDTO
    ) {
        customerAuthService.signup(signupReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 회원가입 성공", null));
    }

    /**
     * 수요자 정보 조회
     * @return 수요자 정보를 담은 응답
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<CustomerInfoDetailRspDTO>> getCustomer(
            @RequestParam Long customerId //TODO 테스트용
            // TODO @AuthenticationPrincipal AuthenticatedUser user
    ){
        CustomerInfoDetailRspDTO infoRspDTO = customerAuthService.getCustomer(customerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 정보 조회 성공", infoRspDTO));
    }

    /**
     * 수요자 정보 수정
     * @param customerId 수요자ID
     * @param updateReqDTO 수요자 정보 수정 요청 DTO
     * @return 수요자 정보를 담은 응답
     */
    @PatchMapping("/my")
    public ResponseEntity<ApiResponse<CustomerInfoDetailRspDTO>> updateCustomer(
            @RequestParam Long customerId, //TODO 테스트용
            // TODO @AuthenticationPrincipal AuthenticatedUser user
            @Valid @RequestBody CustomerInfoUpdateReqDTO updateReqDTO
    ){
        CustomerInfoDetailRspDTO updateRspDTO = customerAuthService.updateCustomer(customerId, updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 정보 수정 성공", updateRspDTO));
    }

    /**
     * 수요자 비밀번호 수정
     * @param customerId 수요자ID
     * @param resetReqDTO 새로운 비밀번호 요청 DTO
     */
    @PatchMapping("/reset-pwd")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @RequestParam Long customerId, //TODO 테스트용
            // TODO @AuthenticationPrincipal AuthenticatedUser user
            @Valid @RequestBody CustomerPasswordResetReqDTO resetReqDTO
    ){
        customerAuthService.resetPassword(customerId, resetReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 비밀번호 수정 성공", null));
    }

    /**
     * 수요자 회원 탈퇴
     * @param customerId 수요자ID
     * @param jsonPassword 수요자 비밀번호
     */
    @DeleteMapping("/my")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @RequestParam Long customerId, //TODO 테스트용
            @RequestBody Map<String, String> jsonPassword
            // TODO @AuthenticationPrincipal AuthenticatedUser user
    ){
        customerAuthService.deleteCustomer(customerId, jsonPassword.get("password"));
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 회원 탈퇴 성공", null));
    }


}
