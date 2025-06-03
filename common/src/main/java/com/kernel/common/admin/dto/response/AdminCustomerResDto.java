package com.kernel.common.admin.dto.response;

import com.kernel.common.admin.entity.AdminCustomer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class AdminCustomerResDto {

    private Long customerId;
    private String email;
    private String userName;
    private String phone;
    private String accountStatus;
    private String gender;
}