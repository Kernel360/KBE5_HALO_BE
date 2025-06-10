package com.kernel.common.customer.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InquiryCategoryRspDTO {

    // 카테고리 ID
    private Long categoryId;

    // 카테고리 명
    private String categoryName;
}
