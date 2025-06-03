package com.kernel.common.admin.dto.mapper;

import com.kernel.common.admin.dto.response.AdminCustomerResDto;
import com.kernel.common.customer.entity.Customer;

public class CustomerMapper {
    public static AdminCustomerResDto from(Customer customer) {
        return AdminCustomerResDto.builder()
                .phone(customer.getPhone())
                .build();
    }
}

