package com.kernel.admin.service.dto.response;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Schema(description = "관리자 응답 DTO")
public class AdminRspDTO {

    @Schema(description = "관리자 ID", example = "1")
    private Long adminId;
}