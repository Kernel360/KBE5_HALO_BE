package com.kernel.member.service.response;


import com.kernel.member.domain.entity.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailInfo {

    // 포인트
    private Integer point;

    // Customer -> CustomerDetailInfo
    public static CustomerDetailInfo fromEntity(Customer customer) {
        return CustomerDetailInfo.builder()
                .point(customer.getPoint())
                .build();
    }
}
