package com.kernel.member.controller;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.service.customer.CustomerAuthService;
import com.kernel.member.service.request.CustomerSignupReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/customer/auth")
@RequiredArgsConstructor
public class CustomerController {

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
            @AuthenticationPrincipal CustomUserDetails customer
    ){
        CustomerInfoDetailRspDTO infoRspDTO = customerAuthService.getCustomer(customer.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 정보 조회 성공", infoRspDTO));
    }

    /**
     * 수요자 정보 수정
     * @param customer 수요자ID
     * @param updateReqDTO 수요자 정보 수정 요청 DTO
     * @return 수요자 정보를 담은 응답
     */
    @PatchMapping("/my")
    public ResponseEntity<ApiResponse<CustomerInfoDetailRspDTO>> updateCustomer(
            @AuthenticationPrincipal CustomUserDetails customer,
            @Valid @RequestBody CustomerInfoUpdateReqDTO updateReqDTO
    ){
        CustomerInfoDetailRspDTO updateRspDTO = customerAuthService.updateCustomer(customer.getUserId(), updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 정보 수정 성공", updateRspDTO));
    }

    /**
     * 수요자 비밀번호 수정
     * @param customer 수요자ID
     * @param resetReqDTO 새로운 비밀번호 요청 DTO
     */
    @PatchMapping("/reset-pwd")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @AuthenticationPrincipal CustomUserDetails customer,
            @Valid @RequestBody CustomerPasswordResetReqDTO resetReqDTO
    ){
        customerAuthService.resetPassword(customer.getUserId(), resetReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 비밀번호 수정 성공", null));
    }

    /**
     * 수요자 회원 탈퇴
     * @param customer 수요자ID
     * @param jsonPassword 수요자 비밀번호
     */
    @DeleteMapping("/my")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @RequestBody Map<String, String> jsonPassword,
            @AuthenticationPrincipal CustomUserDetails customer
    ){
        customerAuthService.deleteCustomer(customer.getUserId(), jsonPassword.get("password"));
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 회원 탈퇴 성공", null));
    }

    /**
     * 수요자 아이디 찾기
     * @param findAccountReqDTO 아이디 찾기 요청 DTO
     * @return 수요자 계정 존재 여부
     */
    @PostMapping("/recovery-id")
    public ResponseEntity<ApiResponse<Boolean>> findCustomerId(
            @RequestBody CustomerFindAccountReqDTO findAccountReqDTO
    ){
        Boolean existsCustomer = customerAuthService.findCustomerId(findAccountReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 아이디 찾기 성공", existsCustomer));
    }

    /**
     * 수요자 비밀번호 찾기
     * @param findAccountReqDTO 아이디 찾기 요청 DTO
     * @return 랜덤 비밀번호
     */
    @PostMapping("/recovery-pwd")
    public ResponseEntity<ApiResponse<String>> findCustomerPassword(
            @RequestBody CustomerFindAccountReqDTO findAccountReqDTO
    ) {
        String randomPassword = customerAuthService.findCustomerPassword(findAccountReqDTO);
        return ResponseEntity.ok((new ApiResponse<>(true, "수요자 비밀번호 찾기 성공", randomPassword)));
    }

}
