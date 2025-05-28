package com.kernel.app.dto.request;


import com.kernel.app.enums.Gender;
import com.kernel.app.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerSignupReqDTO {

    @NotBlank(message = "핸드폰 번호는 필수 입력 값입니다.")
    private String phone;

    @Email(message = "유효한 이에일 주소를 입력해주세요.")
    @Size(max = 100, message = "이메일은 100자 이내로 입력해주세요.")
    private String email;

    @NotBlank
    private String password;
    @NotBlank
    private String userName;

    @NotNull(message = "생년월일은 필수 입력 값입니다.")
    private LocalDate birthDate;

    @NotNull(message = "성별은 필수 입력 값입니다.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotBlank
    private String zipcode;
    @NotBlank
    private String roadAddress;
    @NotBlank
    private String detailAddress;

    @NotBlank
    @Builder.Default
    private Integer point = 0;

    @Enumerated(EnumType.STRING)
    private UserStatus status = UserStatus.ACTIVE;


}
