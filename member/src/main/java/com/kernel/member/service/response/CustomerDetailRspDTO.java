package com.kernel.member.service.response;

import com.kernel.member.service.common.response.UserInfoRspDTO;
import com.kernel.member.service.common.response.UserRspDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDetailRspDTO {

    // User
    private UserRspDTO user;

    // UserInfo
    private UserInfoRspDTO userInfo;

    // Customer
    private CustomerRspDTO customer;

    // UserRspDTO + UserInfoRspDTO + CustomerRspDTO -> CustomerDetailRspDTO
    public static CustomerDetailRspDTO fromDTOs(
            UserRspDTO userRspDTO,
            UserInfoRspDTO userInfoRspDTO,
            CustomerRspDTO customerRspDTO
    ) {
        return CustomerDetailRspDTO.builder()
                .user(userRspDTO)
                .userInfo(userInfoRspDTO)
                .customer(customerRspDTO)
                .build();
    }
}
