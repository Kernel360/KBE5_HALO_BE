package com.kernel.common.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminManagerSummaryResponseDTO {

    private Long managerId;
    private String userName;
    private String status;
    private Integer reservationCount;
    private Integer reviewCount;

}
