package com.kernel.reservation.service.info;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceCategoryTreeInfo {

    // 서비스 ID
    private Long serviceId;

    // 서비스 이름
    private String serviceName;

    // 서비스 제공 시간
    private Integer serviceTime;

    // 카테고리 깊이
    private Integer depth;

    // 가격
    private Integer price;

    // 설명
    private String description;

    // 자식 카테고리
    private List<ServiceCategoryTreeInfo> children;


}
