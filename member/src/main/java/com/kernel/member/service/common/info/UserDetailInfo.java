package com.kernel.member.service.common.info;

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
public class UserDetailInfo {

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

    // UserInfo -> UserDetailInfo
    public static UserDetailInfo fromEntity(UserInfo userInfo) {
        return UserDetailInfo.builder()
                .birthDate(userInfo.getBirthDate())
                .gender(userInfo.getGender())
                .latitude(userInfo.getLatitude())
                .longitude(userInfo.getLongitude())
                .roadAddress(userInfo.getRoadAddress())
                .detailAddress(userInfo.getDetailAddress())
                .build();
    }

}
