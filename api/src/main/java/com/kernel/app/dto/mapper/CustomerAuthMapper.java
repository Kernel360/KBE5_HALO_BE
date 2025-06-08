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
    public Customer toEntity(CustomerSignupReqDTO signupReqDTO){
        return Customer.builder()
                .email(signupReqDTO.getEmail())
                .password(bCryptPasswordEncoder.encode(signupReqDTO.getPassword()))
                .userName(signupReqDTO.getUserName())
                .birthDate(signupReqDTO.getBirthDate())
                .gender(signupReqDTO.getGender())
                .phone(signupReqDTO.getPhone())
                .roadAddress(signupReqDTO.getRoadAddress())
                .detailAddress(signupReqDTO.getDetailAddress())
                .latitude(signupReqDTO.getLatitude())
                .longitude(signupReqDTO.getLongitude())
                .status(signupReqDTO.getStatus())
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
                .roadAddress(customer.getRoadAddress())
                .detailAddress(customer.getDetailAddress())
                .latitude(customer.getLatitude())
                .longitude(customer.getLongitude())
                .point(customer.getPoint())
                .status(customer.getStatus())
                .build();
    }

}
