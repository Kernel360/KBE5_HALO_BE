package com.kernel.member.controller;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.service.common.UserService;
import com.kernel.member.service.common.request.UserFindAccountReqDTO;
import com.kernel.member.service.common.request.UserResetPwdReqDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 사용자 아이디 찾기
     * @param findAccountReqDTO 아이디 찾기 요청 DTO
     * @return 수요자 계정 존재 여부
     */
    @PostMapping("/common/recovery-id")
    public ResponseEntity<ApiResponse<Boolean>> findUserId(
            @Valid @RequestBody UserFindAccountReqDTO findAccountReqDTO
    ){
        Boolean existsCustomer = userService.findUserId(findAccountReqDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 아이디 찾기 성공", existsCustomer));
    }

    /**
     * 사용자 비밀번호 찾기
     * @param findAccountReqDTO 비밀번호 찾기 요청 DTO
     * @return 랜덤 비밀번호
     */
    @PostMapping("/common/recovery-pwd")
    public ResponseEntity<ApiResponse<String>> findCustomerPassword(
            @Valid @RequestBody UserFindAccountReqDTO findAccountReqDTO
    ) {
        String randomPassword = userService.findUserPassword(findAccountReqDTO);
        return ResponseEntity.ok((new ApiResponse<>(true, "사용자 비밀번호 찾기 성공", randomPassword)));
    }

    /**
     * 사용자 비밀번호 확인
     * @param user 로그인한 사용자
     * @param jsonPassword 확인용 비밀번호
     */
    @PostMapping("/auth/password-check")
    public ResponseEntity<ApiResponse<Void>> checkPassword(
            @RequestBody Map<String, String> jsonPassword,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        userService.checkPassword(user.getUserId(), jsonPassword.get("password"));
        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 비밀번호 확인 성공", null));
    }

    /**
     * 수요자 비밀번호 수정
     * @param user 로그인한 사용자
     * @param resetReqDTO 새로운 비밀번호 요청 DTO
     */
    @PatchMapping("/auth/reset-pwd")
    public ResponseEntity<ApiResponse<Void>> resetPassword(
            @AuthenticationPrincipal CustomUserDetails user,
            @Valid @RequestBody UserResetPwdReqDTO resetReqDTO
    ){
        userService.resetPassword(user.getUserId(), resetReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 비밀번호 수정 성공", null));
    }

    /**
     * 사용자 회원 탈퇴
     * @param user 로그인한 사용자
     * @param jsonPassword 확인용 비밀번호
     */
    @DeleteMapping("/auth/my")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(
            @RequestBody Map<String, String> jsonPassword,
            @AuthenticationPrincipal CustomUserDetails user
    ){
        userService.deleteUser(user.getUserId(), jsonPassword.get("password"));
        return ResponseEntity.ok(new ApiResponse<>(true, "사용자 회원 탈퇴 성공", null));
    }

}
