package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.request.AdminBannerReqDTO;
import com.kernel.common.admin.dto.response.AdminBannerRspDTO;
import com.kernel.common.global.entity.Banner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminBannerMapper {

    // AdminBannerReqDTO to Banner Entity
    public Banner toBannerEntity(AdminBannerReqDTO dto) {
        return Banner.builder()
                .title(dto.getTitle())
                .path(dto.getPath())
                .startAt(dto.getStartAt())
                .endAt(dto.getEndAt())
                .fileId(dto.getFileId())
                .build();
    }

    // Banner Entity to AdminBannerRspDTO
    public AdminBannerRspDTO toAdminBannerRspDTO(Banner banner) {
        return AdminBannerRspDTO.builder()
                .bannerId(banner.getBannerId())
                .title(banner.getTitle())
                .path(banner.getPath())
                .startAt(banner.getStartAt())
                .endAt(banner.getEndAt())
                .views(banner.getViews())
                .fileId(banner.getFileId())
                .createdAt(banner.getCreatedAt())
                .createdBy(banner.getCreatedBy())
                .bannerStatus(banner.getBannerStatus())
                .build();
    }
}
