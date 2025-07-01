package com.kernel.member.service.response;

import com.kernel.member.common.enums.Gender;
import com.kernel.member.service.common.info.CustomerDetailInfo;
import com.kernel.member.service.common.info.UserDetailInfo;
import com.kernel.member.service.common.info.UserAccountInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "고객 상세 응답 DTO")
public class CustomerDetailRspDTO {

    /* User */
    @Schema(description = "핸드폰 번호", example = "010-1234-5678", required = true)
    private String phone;

    @Schema(description = "이메일 주소", example = "example@email.com", required = true)
    private String email;

    @Schema(description = "사용자 이름", example = "홍길동", required = true)
    private String userName;

    /* UserInfo */
    @Schema(description = "생년월일", example = "1990-01-01", required = true)
    private LocalDate birthDate;

    @Schema(description = "성별", example = "MALE", required = true)
    private Gender gender;

    @Schema(description = "위도", example = "37.5665", required = true)
    private BigDecimal latitude;

    @Schema(description = "경도", example = "126.9780", required = true)
    private BigDecimal longitude;

    @Schema(description = "도로명 주소", example = "서울특별시 중구 세종대로 110", required = true)
    private String roadAddress;

    @Schema(description = "상세 주소", example = "10층", required = true)
    private String detailAddress;

    /* Customer */
    @Schema(description = "포인트", example = "1000", required = true)
    private Integer point;

    @Schema(description = "UserAccountInfo, UserDetailInfo, CustomerDetailInfo를 CustomerDetailRspDTO로 변환")
    public static CustomerDetailRspDTO fromInfos(
            UserAccountInfo userAccountInfo,
            UserDetailInfo userDetailInfo,
            CustomerDetailInfo customerDetailInfo
    ) {
        return CustomerDetailRspDTO.builder()
                .phone(userAccountInfo.getPhone())
                .email(userAccountInfo.getEmail())
                .userName(userAccountInfo.getUserName())
                .birthDate(userDetailInfo.getBirthDate())
                .gender(userDetailInfo.getGender())
                .latitude(userDetailInfo.getLatitude())
                .longitude(userDetailInfo.getLongitude())
                .roadAddress(userDetailInfo.getRoadAddress())
                .detailAddress(userDetailInfo.getDetailAddress())
                .point(customerDetailInfo.getPoint())
                .build();
    }
}