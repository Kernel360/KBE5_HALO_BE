package com.kernel.common.admin.dto.response;

import com.kernel.common.reservation.entity.ServiceCategory;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminServiceCategoryRspDTO {

    private Long serviceId;
    private ServiceCategory parentId;
    private String serviceName;
    private Integer depth;
    private Integer sortOrder;
    private Integer serviceTime;

}
