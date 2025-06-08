package com.kernel.common.customer.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerInfoUpdateReqDTO {

    // 이메일
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Size(max = 50)
    private String email;

    // 확인용 비밀번호
    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String password;

    // 도로명주소
    @NotBlank(message = "도로명 주소를 입력해주세요.")
    private String roadAddress;

    // 상세주소
    @NotBlank(message = "상세 주소를 입력해주세요.")
    private String detailAddress;

    // 위도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    // 경도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal longitude;

}
