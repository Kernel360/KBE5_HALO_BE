package com.kernel.common.admin.dto.request;

import com.kernel.common.global.entity.ServiceCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminServiceCatReqDTO {
    // 서비스 카테고리 등록 및 수정 요청에 사용하는 DTO


    private ServiceCategory parentId;

    @NotBlank(message = "서비스 카테고리 이름은 필수입니다.")
    @NotNull
    private String serviceName;

    @Builder.Default
    private Boolean isActive = true;

    @Builder.Default
    private Integer sortOrder = 0;

    @NotNull(message = "서비스 최소 제공 시간은 필수입니다.")
    private Integer serviceTime;

}
