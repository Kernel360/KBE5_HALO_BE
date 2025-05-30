package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.request.AdminServiceCatReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCatRspDTO;
import com.kernel.common.global.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceMapper {

    // toReqDTO -> toEntity
    public ServiceCategory toServiceCat(AdminServiceCatReqDTO request) {
        return ServiceCategory.builder()
                .parentId(request.getParentId())
                .serviceName(request.getServiceName())
                .isActive(request.getIsActive())
                .sortOrder(request.getSortOrder())
                .serviceTime(request.getServiceTime())
                .build();
    }

    // Entity -> toRspDTO
    public AdminServiceCatRspDTO toServiceCatRspDTO(ServiceCategory serviceCategory) {
        return AdminServiceCatRspDTO.builder()
                .serviceId(serviceCategory.getServiceId())
                .parentId(serviceCategory.getParentId().getServiceId())     // 부모 ID를 조회하면 객체가 나오므로, 그 객체의 ID를 가져와야 함.
                .serviceName(serviceCategory.getServiceName())
                .depth(serviceCategory.getDepth())
                .sortOrder(serviceCategory.getSortOrder())
                .serviceTime(serviceCategory.getServiceTime())
                .build();
    }

    // toRspDTO -> toListRspDTO
    public List<AdminServiceCatRspDTO> toServiceCatRspDTOList(List<ServiceCategory> serviceCategories) {
        return serviceCategories.stream()
                .map(this::toServiceCatRspDTO)
                .toList();
    }

}
