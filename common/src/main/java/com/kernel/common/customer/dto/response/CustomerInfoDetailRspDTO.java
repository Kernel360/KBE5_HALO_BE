package com.kernel.common.customer.dto.response;

import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoDetailRspDTO {

    // 핸드폰번호
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String username;

    // 생년월일
    private LocalDate birthDate;

    // 성별
    private Gender gender;

    // 우편번호
    private String zipcode;

    // 도로명주소
    private String roadAddress;

    // 상세주소
    private String detailAddress;

    // 포인트
    private Integer point;

    // 상태값
    private UserStatus status;

}
