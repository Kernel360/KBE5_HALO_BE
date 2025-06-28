package com.kernel.member.service.common.request;

import com.kernel.global.domain.entity.User;
import com.kernel.member.common.enums.Gender;
import com.kernel.member.domain.entity.UserInfo;
import jakarta.validation.constraints.*;
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

    // 도로명 주소
    @NotBlank(message = "주소를 입력해주세요.")
    private String roadAddress;

    // 상세 주소
    @NotBlank(message = "주소를 입력해주세요.")
    private String detailAddress;

    // UserInfoSignupReqDTO -> UserInfo
    public UserInfo toEntity(User user){
        return UserInfo.builder()
                .user(user)
                .birthDate(birthDate)
                .gender(gender)
                .latitude(latitude)
                .longitude(longitude)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .build();
    }
}
