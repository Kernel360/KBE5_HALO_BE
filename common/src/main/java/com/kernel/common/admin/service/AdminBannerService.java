package com.kernel.common.admin.service;

import com.kernel.common.admin.dto.request.AdminBannerReqDTO;
import com.kernel.common.admin.dto.response.AdminBannerRspDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdminBannerService {

    void createBanner(AdminBannerReqDTO bannerDto);
    void updateBanner(Long bannerId, AdminBannerReqDTO bannerDto);
    Page<AdminBannerRspDTO> getBanners(Pageable pageable);
    AdminBannerRspDTO getBanner(Long bannerId);
    void deleteBanner(Long bannerId);
}
