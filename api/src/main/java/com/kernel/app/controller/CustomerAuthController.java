package com.kernel.app.controller;



import com.kernel.app.dto.request.CustomerSignupReqDTO;
import com.kernel.app.dto.request.UserLoginReqDTO;
import com.kernel.app.service.CustomerAuthService;
import com.kernel.app.entity.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerAuthController {

    private final CustomerAuthService customerAuthService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Void>> join(@RequestBody CustomerSignupReqDTO joinDTO){
        customerAuthService.join(joinDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Void>> login(@RequestBody UserLoginReqDTO loginDTO){

        customerAuthService.login(loginDTO);

        return ResponseEntity.ok(new ApiResponse<>(true, "success", null));
    }


}
