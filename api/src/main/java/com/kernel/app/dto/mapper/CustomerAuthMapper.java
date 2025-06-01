package com.kernel.app.dto.mapper;

import com.kernel.common.customer.dto.request.CustomerSignupReqDTO;
import com.kernel.common.customer.dto.response.CustomerInfoDetailRspDTO;
import com.kernel.common.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerAuthMapper {

   private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // CustomerSignupReqDTO -> Customer (회원가입)
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

    // Customer -> CustomerInfoDetailRspDTO (유저 상세 정보)
    public CustomerInfoDetailRspDTO toInfoDto(Customer customer){
        return CustomerInfoDetailRspDTO.builder()
                .phone(customer.getPhone())
                .email(customer.getEmail())
                .username(customer.getUserName())
                .birthDate(customer.getBirthDate())
                .gender(customer.getGender())
                .zipcode(customer.getZipcode())
                .roadAddress(customer.getRoadAddress())
                .detailAddress(customer.getDetailAddress())
                .point(customer.getPoint())
                .status(customer.getStatus())
                .build();
    }

}
