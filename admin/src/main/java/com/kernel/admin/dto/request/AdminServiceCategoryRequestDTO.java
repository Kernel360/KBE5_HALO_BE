package com.kernel.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminServiceCategoryRequestDTO {
    private Long parentId;
    private String serviceName;
    private Boolean isActive;
    private Integer sortOrder;
    private Time serviceTime;
}
