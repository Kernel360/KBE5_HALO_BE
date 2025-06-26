package com.kernel.admin.controller;



import com.kernel.admin.service.AdminAccountService;
import com.kernel.admin.service.dto.request.AdminSearchReqDTO;
import com.kernel.admin.service.dto.response.AdminSearchRspDTO;
import com.kernel.global.service.dto.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/accounts")
@RequiredArgsConstructor
public class AdminAccountController {

    private final AdminAccountService adminAccountService;

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminSearchRspDTO>>> getAdminList(
            @ModelAttribute @Valid AdminSearchReqDTO request,
            Pageable pageable
    ) {

        Page<AdminSearchRspDTO> adminPage = adminAccountService.searchAdminList(request, pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "관리자 목록 조회 성공", adminPage));
    }
}
