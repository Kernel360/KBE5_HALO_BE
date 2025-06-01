package com.kernel.common.admin.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class AdminRspDTO {
    private Long adminId;
}
