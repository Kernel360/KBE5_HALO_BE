package com.kernel.member.service.request;

import com.kernel.member.common.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@Schema(description = "사용자 정보 회원가입 요청 DTO")
public class UserInfoSignupReqDTO {

    @Schema(description = "생년월일", example = "1990-01-01", required = true)
    @NotNull(message = "생년월일을 입력해주세요.")
    private LocalDate birthDate;

    @Schema(description = "성별", example = "MALE", required = true)
    @NotNull(message = "성별을 선택해주세요.")
    private Gender gender;

    @Schema(description = "위도", example = "37.5665", required = true)
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal latitude;

    @Schema(description = "경도", example = "126.9780", required = true)
    @NotNull(message = "주소를 입력해주세요.")
    private BigDecimal longitude;

    @Schema(description = "도로명 주소", example = "서울특별시 중구 세종대로 110", required = true)
    @NotBlank(message = "주소를 입력해주세요.")
    private String roadAddress;

    @Schema(description = "상세 주소", example = "10층", required = true)
    @NotBlank(message = "주소를 입력해주세요.")
    private String detailAddress;
}