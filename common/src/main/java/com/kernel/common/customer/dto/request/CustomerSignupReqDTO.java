package com.kernel.common.customer.dto.request;


import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerSignupReqDTO {

    // 핸드폰 번호
    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phone;

    // 이메일
    @Email(message = "유효한 이에일 주소를 입력해주세요.")
    @Size(max = 100, message = "이메일은 100자 이내로 입력해주세요.")
    private String email;

    // 비밀번호
    @NotBlank
    private String password;

    // 이름
    @NotBlank
    private String userName;

    // 생년월일
    @NotNull(message = "생년월일은 필수 입력 값입니다.")
    private LocalDate birthDate;

    // 성별
    @NotNull(message = "성별은 필수 입력 값입니다.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    // 도로명 주소
    @NotBlank(message = "주소를 입력해주세요.")
    private String roadAddress;

    // 상세 주소
    @NotBlank(message = "주소를 입력해주세요.")
    private String detailAddress;

    // 위도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    // 경도
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal longitude;

    // 포인트
    @Builder.Default
    private Integer point = 0;

    // 계정 상태
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private UserStatus status = UserStatus.ACTIVE;

}
