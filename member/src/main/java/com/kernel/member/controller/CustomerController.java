package com.kernel.member.controller;

import com.kernel.global.security.CustomUserDetails;
import com.kernel.global.service.dto.response.ApiResponse;
import com.kernel.member.common.enums.PointChargeType;
import com.kernel.member.service.CustomerService;
import com.kernel.member.service.request.ChargePointReqDTO;
import com.kernel.member.service.request.CustomerSignupReqDTO;
import com.kernel.member.service.request.CustomerUpdateReqDTO;
import com.kernel.member.service.response.CustomerDetailRspDTO;
import com.kernel.member.service.response.CustomerPointRspDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Customer API", description = "수요자 계정 관리 API")
@RestController
@RequestMapping("/api/customers/auth")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    /**
     * 수요자 회원가입
     * @param signupReqDTO 회원가입 요청 DTO
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> signup(
            @Valid @RequestBody CustomerSignupReqDTO signupReqDTO
    ) {
        customerService.signup(signupReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 회원가입 성공", null));
    }

    /**
     * 수요자 정보 조회
     * @return 수요자 정보를 담은 응답
     */
    @GetMapping("/my")
    public ResponseEntity<ApiResponse<CustomerDetailRspDTO>> getCustomer(
            @AuthenticationPrincipal CustomUserDetails customer
    ){
        CustomerDetailRspDTO infoRspDTO = customerService.getCustomer(customer.getUserId());
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 정보 조회 성공", infoRspDTO));
    }

    /**
     * 수요자 정보 수정
     * @param customer 수요자ID
     * @param updateReqDTO 수요자 정보 수정 요청 DTO
     * @return 수요자 정보를 담은 응답
     */
    @PatchMapping("/my")
    public ResponseEntity<ApiResponse<CustomerDetailRspDTO>> updateCustomer(
            @AuthenticationPrincipal CustomUserDetails customer,
            @Valid @RequestBody CustomerUpdateReqDTO updateReqDTO
    ){
        CustomerDetailRspDTO updateRspDTO = customerService.updateCustomer(customer.getUserId(), updateReqDTO);
        return ResponseEntity.ok(new ApiResponse<>(true, "수요자 정보 수정 성공", updateRspDTO));
    }

    /**
     * 포인트 추가
     * @param customer 수요자ID
     * @param pointReqDTO 충전 포인트
     * @return 수요자 정보를 담은 응답
     */
    @PatchMapping("/my/point")
    public ResponseEntity<ApiResponse<CustomerDetailRspDTO>> chargePoint(
            @AuthenticationPrincipal CustomUserDetails customer,
            @Valid @RequestBody ChargePointReqDTO pointReqDTO
    ) {

        customerService.chargePoint(customer.getUserId(), pointReqDTO.getPoint(), PointChargeType.CHARGE);
        CustomerDetailRspDTO infoRspDTO = customerService.getCustomer(customer.getUserId());

        return ResponseEntity.ok(new ApiResponse<>(true, "포인트 충전 성공", infoRspDTO));
    }

    /**
     * 포인트 조회
     * @param customer 수요자ID
     * @return 수요자 정보를 담은 응답
     */
    @GetMapping("/my/point")
    public ResponseEntity<ApiResponse<CustomerPointRspDTO>> chargePoint(
            @AuthenticationPrincipal CustomUserDetails customer
    ) {

        Integer getPoint = customerService.getCustomerPoints(customer.getUserId());
        CustomerPointRspDTO rspDTO = CustomerPointRspDTO.builder().point(getPoint).build();

        return ResponseEntity.ok(new ApiResponse<>(true, "포인트 조회 성공", rspDTO));
    }


}
