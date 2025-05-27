package com.kernel.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminServiceCategoryResponseDTO {
    private Long serviceId;
    private Long parentId;
    private String serviceName;
    private Boolean isActive;
    private Integer depth;
    private Integer sortOrder;
    private Time serviceTime;
    //private Integer depth;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;
}
