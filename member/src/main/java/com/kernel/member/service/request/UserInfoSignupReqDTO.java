package com.kernel.member.service.request;

import com.kernel.member.common.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class UserInfoSignupReqDTO {

    // 생년월일
    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birthDate;

    // 성별
    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    // 위도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    // 경도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal longitude;

    // 도로명 주소
    @NotBlank(message = "주소를 입력해주세요.")
    private String roadAddress;

    // 상세 주소
    @NotBlank(message = "주소를 입력해주세요.")
    private String detailAddress;
}
