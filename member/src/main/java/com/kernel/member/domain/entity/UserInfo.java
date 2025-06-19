package com.kernel.member.domain.entity;

import com.kernel.member.domain.enumerate.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UserInfo {

    // 사용자 정보Id
    private Long infoId;

    // 생년월일
    private LocalDate birthDate;

    // 성별
    private Gender gender;

    // 위도
    private BigDecimal latitude;

    // 경도
    private BigDecimal longitude;

    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;
}
