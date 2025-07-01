package com.kernel.common.manager.dto.response;

import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ManagerInfoRspDTO {

    // 매니저ID
    private Long managerId;

    // 연락처(=계정ID)
    private String phone;

    // 이메일
    private String email;

    // 이름
    private String userName;

    // 생년월일
    private LocalDate birthDate;

    // 성별
    private Gender gender;

    // 성별 라벨
    private String genderName;

    // 위도
    private BigDecimal latitude;

    // 경도
    private BigDecimal longitude;

    // 도로명 주소
    private String roadAddress;

    // 상세 주소
    private String detailAddress;

    // 한줄소개
    private String bio;

    // 프로필이미지ID
    private Long profileImageId;

    // 첨부파일ID
    private Long fileId;

    // 계정 상태
    private UserStatus status;

    // 계정 상태 라벨
    private String statusName;

    // 매니저 업무 가능 시간
    private List<AvailableTimeRspDTO> availableTimes;

    // 계약일시
    private LocalDateTime contractAt;

    // 계약해지사유
    private String terminationReason;

    // 계약해지일시
    private LocalDateTime terminatedAt;
}
