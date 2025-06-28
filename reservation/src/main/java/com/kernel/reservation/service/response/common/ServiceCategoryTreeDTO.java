package com.kernel.reservation.service.response.common;

import com.kernel.reservation.service.info.ServiceCategoryTreeInfo;
import lombok.Builder;
import lombok.Getter;

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

    // ServiceCategoryTreeInfo -> ServiceCategoryTreeDTO
    public static ServiceCategoryTreeDTO fromInfo(ServiceCategoryTreeInfo info) {
        if( info == null ) return null;

        List<ServiceCategoryTreeDTO> childDTOs = null;

        if(info.getChildren() != null ) {
            childDTOs = info.getChildren().stream()
                    .map(ServiceCategoryTreeDTO::fromInfo)
                    .toList();
        }

        return ServiceCategoryTreeDTO.builder()
                .serviceId(info.getServiceId())
                .serviceName(info.getServiceName())
                .serviceTime(info.getServiceTime())
                .depth(info.getDepth())
                .price(info.getPrice())
                .description(info.getDescription())
                .children(childDTOs)
                .build();
    }

}
