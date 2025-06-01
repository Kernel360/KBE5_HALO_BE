package com.kernel.app.controller;

import com.kernel.app.service.AdminAuthService;
import com.kernel.common.admin.dto.AdminSignupReqDTO;
import com.kernel.common.global.entity.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/admins/auth")
@RequiredArgsConstructor
public class AdminAuthController {

    private final AdminAuthService adminAuthService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> join(@Valid @RequestBody AdminSignupReqDTO joinDTO){
        adminAuthService.join(joinDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }


}
