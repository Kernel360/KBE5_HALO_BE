package com.kernel.admin.controller;



import com.kernel.admin.service.AdminBannerService;
import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import com.kernel.admin.service.dto.response.AdminBannerRspDTO;
import com.kernel.global.service.dto.response.ApiResponse;
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

    /**
     * 배너 생성
     *
     * @param request 배너 요청 DTO
     * @return 배너 생성 성공 응답
     */
    @PostMapping
    public ResponseEntity<ApiResponse<Void>> createBanner(
            @RequestBody @Valid AdminBannerReqDTO request
    ) {
        adminBannerService.createBanner(request);

        return ResponseEntity.ok(new ApiResponse<>(true, "배너 생성 성공", null));
    }

    /**
     * 배너 목록 조회
     *
     * @param pageable 페이징 정보
     * @return 배너 목록 페이지 응답
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<AdminBannerRspDTO>>> getBanners(
            Pageable pageable
    ) {
        Page<AdminBannerRspDTO> banners = adminBannerService.getBanners(pageable);

        return ResponseEntity.ok(new ApiResponse<>(true, "배너 목록 조회 성공", banners));
    }

    /**
     * 배너 상세 조회
     *
     * @param bannerId 배너 ID
     * @return 배너 상세 응답
     */
    @GetMapping("/{banner-id}")
    public ResponseEntity<ApiResponse<AdminBannerRspDTO>> getBanner(
            @PathVariable("banner-id") Long bannerId
    ) {
        AdminBannerRspDTO banner = adminBannerService.getBanner(bannerId);

        return ResponseEntity.ok(new ApiResponse<>(true, "배너 조회 성공", banner));
    }

    /**
     * 배너 수정
     *
     * @param bannerId 배너 ID
     * @param request  배너 요청 DTO
     * @return 배너 수정 성공 응답
     */
    @PatchMapping("/{banner-id}")
    public ResponseEntity<ApiResponse<Void>> updateBanner(
            @PathVariable("banner-id") Long bannerId,
            @RequestBody @Valid AdminBannerReqDTO request
    ) {
        adminBannerService.updateBanner(bannerId, request);

        return ResponseEntity.ok(new ApiResponse<>(true, "배너 수정 성공", null));
    }

    /**
     * 배너 삭제
     *
     * @param bannerId 배너 ID
     * @return 배너 삭제 성공 응답
     */
    @DeleteMapping("/{banner-id}")
    public ResponseEntity<ApiResponse<Void>> deleteBanner(
            @PathVariable("banner-id") Long bannerId
    ) {
        adminBannerService.deleteBanner(bannerId);

        return ResponseEntity.ok(new ApiResponse<>(true, "배너 삭제 성공", null));
    }


}
