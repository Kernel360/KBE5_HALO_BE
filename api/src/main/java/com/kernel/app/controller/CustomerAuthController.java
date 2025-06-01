package com.kernel.app.controller;


import com.kernel.app.service.CustomerAuthService;
import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/customers/auth")
@RequiredArgsConstructor
public class CustomerAuthController {

    private final CustomerAuthService customerAuthService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> join(@Valid @RequestBody CustomerSignupReqDTO joinDTO){
        customerAuthService.join(joinDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }


}
