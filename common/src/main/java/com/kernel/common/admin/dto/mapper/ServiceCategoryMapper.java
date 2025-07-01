package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.request.AdminServiceCategoryReqDTO;
import com.kernel.common.admin.dto.response.AdminServiceCategoryRspDTO;
import com.kernel.common.reservation.entity.ServiceCategory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceCategoryMapper {

    // toAdminServiceCategoryReqDTO -> toEntity
    public ServiceCategory toServiceCategory(AdminServiceCategoryReqDTO request, ServiceCategory parentCategory) {
        return ServiceCategory.builder()
                .parentId(parentCategory)
                .serviceName(request.getServiceName())
                .isActive(request.getIsActive())
                .sortOrder(request.getSortOrder())
                .serviceTime(request.getServiceTime())
                .build();
    }

    // Entity -> toServiceCategoryRspDTO
    public AdminServiceCategoryRspDTO toServiceCategoryRspDTO(ServiceCategory serviceCategory) {
        return AdminServiceCategoryRspDTO.builder()
                .serviceId(serviceCategory.getServiceId())
                .parentId(serviceCategory.getParentId() != null ? serviceCategory.getParentId() : null)
                .serviceName(serviceCategory.getServiceName())
                .depth(serviceCategory.getDepth())
                .sortOrder(serviceCategory.getSortOrder())
                .serviceTime(serviceCategory.getServiceTime())
                .build();
    }

    // toServiceCategoryRspDTO -> toServiceCategoriesRspDTO
    public List<AdminServiceCategoryRspDTO> toServiceCategoriesRspDTO(List<ServiceCategory> serviceCategories) {
        return serviceCategories.stream()
                .map(this::toServiceCategoryRspDTO)
                .toList();
    }

}
