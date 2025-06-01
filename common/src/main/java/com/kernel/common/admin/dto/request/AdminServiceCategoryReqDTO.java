package com.kernel.common.admin.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminServiceCategoryReqDTO {
    // 서비스 카테고리 등록 및 수정 요청에 사용하는 DTO

    private Long parentId;

    @NotBlank(message = "서비스 카테고리 이름은 필수입니다.")
    private String serviceName;

    private Boolean isActive;

    private Integer sortOrder;

    @NotNull(message = "서비스 최소 제공 시간은 필수입니다.")
    @Min(value = 1, message = "서비스 최소 제공 시간은 1시간 이상이어야 합니다.")
    private Integer serviceTime;

}
