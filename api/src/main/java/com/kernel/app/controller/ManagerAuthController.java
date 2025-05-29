package com.kernel.app.controller;

import com.kernel.app.service.ManagerAuthService;
import com.kernel.common.global.entity.ApiResponse;
import com.kernel.common.manager.dto.ManagerSignupReqDTO;
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
@RequestMapping("/api/managers")
@RequiredArgsConstructor
public class ManagerAuthController {

    private final ManagerAuthService managerAuthService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> join(@Valid @RequestBody ManagerSignupReqDTO joinDTO){
        managerAuthService.join(joinDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }


}
