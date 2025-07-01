package com.kernel.reservation.service.response.common;

import com.kernel.sharedDomain.domain.entity.ServiceCategory;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class ServiceCategoryTreeDTO {

    private Long serviceId;         // 서비스 ID
    private String serviceName;     // 서비스 이름
    private Integer serviceTime;    // 서비스 제공 시간
    private Integer depth;          // 카테고리 깊이
    private Integer price;          // 가격
    private String description;     // 설명

    private List<ServiceCategoryTreeDTO> children;  // 자식 카테고리

    public static ServiceCategoryTreeDTO fromEntity(ServiceCategory entity){
        return ServiceCategoryTreeDTO.builder()
                .serviceId(entity.getServiceId())
                .serviceName(entity.getServiceName())
                .serviceTime(entity.getServiceTime())
                .depth(entity.getDepth())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .children(new ArrayList<>())
                .build();
    }
}
