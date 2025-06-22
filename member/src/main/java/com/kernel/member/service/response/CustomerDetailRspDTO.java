package com.kernel.member.service.response;

import com.kernel.member.common.enums.Gender;
import com.kernel.member.service.common.info.UserDetailInfo;
import com.kernel.member.service.common.info.UserAccountInfo;
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
public class CustomerDetailRspDTO {

    /* User */
    // 핸드폰 번호
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String userName;

    /* UserInfo */
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

    /* Customer */
    // 포인트
    private Integer point;

    // UserAccountInfo + UserDetailInfo + CustomerDetailInfo -> CustomerDetailRspDTO
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
