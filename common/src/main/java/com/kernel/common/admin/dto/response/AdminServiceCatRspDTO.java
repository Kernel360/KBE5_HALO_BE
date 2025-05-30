package com.kernel.common.admin.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminServiceCatRspDTO {
    // 관리자 서비스 카테고리 응답 DTO 정의
    // TODO: 관리자와 수요자가 조회하는 서비스 카테고리 응답이 동일할 수 있으므로, 공통 DTO로 정의할 수 있는지 검토 필요

    private Long serviceId;
    private Long parentId;
    private String serviceName;
    private Integer depth;
    private Integer sortOrder;
    private Integer serviceTime;

}
