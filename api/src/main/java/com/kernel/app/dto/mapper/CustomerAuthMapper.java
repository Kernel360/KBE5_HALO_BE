package com.kernel.app.dto.mapper;

import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;
import com.kernel.common.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerAuthMapper {

   private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // RequestDTO -> Entity
    public Customer toEntity(CustomerSignupReqDTO dto){
        return Customer.builder()
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .phone(dto.getPhone())
                .zipcode(dto.getZipcode())
                .roadAddress(dto.getRoadAddress())
                .detailAddress(dto.getDetailAddress())
                .status(dto.getStatus())
                .build();
    }

}
