package com.kernel.common.customer.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class CustomerFindAccountReqDTO {

    // 핸드폰 번호
    @NotBlank(message = "핸드폰 번호를 입력해주세요.")
    private String phone;

    // 이름
    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    // 생년월일
    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birthDate;
}
