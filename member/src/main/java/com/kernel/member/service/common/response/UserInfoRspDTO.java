package com.kernel.member.service.common.response;

import com.kernel.member.common.enums.Gender;
import com.kernel.member.domain.entity.UserInfo;
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
public class UserInfoRspDTO {

    // 생년월일
    private LocalDate birthDate;

    // 성별
    private Gender gender;

    // 위도
    private BigDecimal latitude;

    // 경도
    private BigDecimal longitude;

    // 도로명주소
    private String roadAddress;

    // 상세주소
    private String detailAddress;

    // UserInfo -> UserInfoRspDTO
    public static UserInfoRspDTO fromEntity(UserInfo userInfo) {
        return UserInfoRspDTO.builder()
                .birthDate(userInfo.getBirthDate())
                .gender(userInfo.getGender())
                .latitude(userInfo.getLatitude())
                .longitude(userInfo.getLongitude())
                .roadAddress(userInfo.getRoadAddress())
                .detailAddress(userInfo.getDetailAddress())
                .build();
    }

}
