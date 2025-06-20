package com.kernel.admin.controller.banner;

import com.kernel.admin.service.banner.AdminBannerService;
import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import com.kernel.admin.service.dto.responsse.AdminBannerRspDTO;
import com.kernel.global.service.dto.responsse.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/banner")
@RequiredArgsConstructor
public class AdminBannerController {

    private final AdminBannerService adminBannerService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createBanner(@RequestBody @Valid AdminBannerReqDTO request) {
        adminBannerService.createBanner(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "배너 생성 성공", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminBannerRspDTO>>> getBanners(Pageable pageable) {
        Page<AdminBannerRspDTO> banners = adminBannerService.getBanners(pageable);
        return ResponseEntity.ok(new ApiResponse<>(true, "배너 목록 조회 성공", banners));
    }

    @GetMapping("/{banner-id}")
    public ResponseEntity<ApiResponse<AdminBannerRspDTO>> getBanner(@PathVariable("banner-id") Long bannerId) {
        AdminBannerRspDTO banner = adminBannerService.getBanner(bannerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "배너 조회 성공", banner));
    }

    @PatchMapping("/{banner-id}")
    public ResponseEntity<ApiResponse<Void>> updateBanner(
            @PathVariable("banner-id") Long bannerId,
            @RequestBody @Valid AdminBannerReqDTO request) {
        adminBannerService.updateBanner(bannerId, request);
        return ResponseEntity.ok(new ApiResponse<>(true, "배너 수정 성공", null));
    }

    @DeleteMapping("/{banner-id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(@PathVariable("banner-id") Long bannerId) {
        adminBannerService.deleteBanner(bannerId);
        return ResponseEntity.ok(new ApiResponse<>(true, "배너 삭제 성공", null));
    }
}
