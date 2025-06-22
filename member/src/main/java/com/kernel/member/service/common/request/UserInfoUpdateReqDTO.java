package com.kernel.member.service.common.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoUpdateReqDTO {

    // 도로명주소
    @NotBlank(message = "도로명 주소를 입력해주세요.")
    private String roadAddress;

    // 상세주소
    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String detailAddress;

    // 위도
    @DecimalMin(value = "-90.0", message = "주소를 다시 선택해주세요.")
    @DecimalMax(value = "90.0", message = "주소를 다시 선택해주세요.")
    @Digits(integer = 2, fraction = 7, message = "주소를 다시 선택해주세요.")
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    // 경도
    @DecimalMin(value = "-180.0", inclusive = true, message = "주소를 다시 선택해주세요.")
    @DecimalMax(value = "180.0", inclusive = true, message = "주소를 다시 선택해주세요.")
    @Digits(integer = 3, fraction = 7, message = "주소를 다시 선택해주세요.")
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal longitude;
}
