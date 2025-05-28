package com.kernel.app.dto.mapper;

import com.kernel.app.dto.request.CustomerSignupReqDTO;
import com.kernel.app.entity.Customer;
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
                .userName(dto.getUsername())
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
