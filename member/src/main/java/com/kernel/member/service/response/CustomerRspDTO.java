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
public class CustomerRspDTO {

    // 포인트
    private Integer point;

    // Customer -> CustomerRspDTO
    public static CustomerRspDTO fromEntity(Customer customer) {
        return CustomerRspDTO.builder()
                .point(customer.getPoint())
                .build();
    }
}
