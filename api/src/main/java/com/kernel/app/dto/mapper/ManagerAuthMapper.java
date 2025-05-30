package com.kernel.app.dto.mapper;

import com.kernel.common.manager.entity.Manager;
import com.kernel.common.manager.dto.ManagerSignupReqDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ManagerAuthMapper {

   private final BCryptPasswordEncoder bCryptPasswordEncoder;

    // RequestDTO -> Entity
    public Manager toEntity(ManagerSignupReqDTO dto){
        return Manager.builder()
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .password(bCryptPasswordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .birthDate(dto.getBirthDate())
                .gender(dto.getGender())
                .zipcode(dto.getZipcode())
                .roadAddress(dto.getRoadAddress())
                .detailAddress(dto.getDetailAddress())
                .bio(dto.getBio())
                .status(dto.getStatus())
                .build();
    }

}
