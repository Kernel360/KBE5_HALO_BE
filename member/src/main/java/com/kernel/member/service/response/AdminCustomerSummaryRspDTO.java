package com.kernel.member.service.response;

import com.kernel.member.service.common.info.AdminCustomerSummaryInfo;
import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AdminCustomerSummaryRspDTO {

    private Long customerId;
    private String email;
    private String userName;
    private String phone;
    private String accountStatus;
    private Integer point;


    // Page<info> -> Page<AdminCustomerSummaryRspDto> 변환 메서드
    public static Page<AdminCustomerSummaryRspDTO> toDTOPage(Page<AdminCustomerSummaryInfo> customers) {
        return customers.map(customer -> AdminCustomerSummaryRspDTO.builder()
                .customerId(customer.getUserId())
                .email(customer.getEmail())
                .userName(customer.getUserName())
                .phone(customer.getPhone())
                .accountStatus(customer.getStatus().name())
                .point(customer.getPoint())
                .build());
    }

}