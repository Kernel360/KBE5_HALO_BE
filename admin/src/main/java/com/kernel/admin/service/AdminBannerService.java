package com.kernel.admin.service;


import com.kernel.admin.service.dto.request.AdminBannerReqDTO;
import com.kernel.admin.service.dto.response.AdminBannerRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminBannerService {

    void createBanner(AdminBannerReqDTO bannerDto);
    void updateBanner(Long bannerId, AdminBannerReqDTO bannerDto);
    Page<AdminBannerRspDTO> getBanners(Pageable pageable);
    AdminBannerRspDTO getBanner(Long bannerId);
    void deleteBanner(Long bannerId);
}
