package com.kernel.member.service.request;

import com.kernel.global.domain.entity.User;
import com.kernel.member.domain.entity.Customer;
import com.kernel.member.domain.entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerReqDTO {

    //포인트
    @Builder.Default
    private Integer point = 0;

    // CustomerReqDTO -> Customer
    public Customer toEntity(User user, UserInfo info){
        return Customer.builder()
                .user(user)
                .point(point)
                .build();
    }

}