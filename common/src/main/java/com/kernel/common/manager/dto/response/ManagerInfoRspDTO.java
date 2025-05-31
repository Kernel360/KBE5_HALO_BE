package com.kernel.common.manager.dto.response;

import com.kernel.common.global.enums.Gender;
import com.kernel.common.global.enums.UserStatus;
import java.time.LocalDate;
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

    // 우편번호
    // TODO: 구글맵API 사용 시, 필요한 컬럼만 정리 필요
    private String zipcode;

    // 도로명 주소
    // TODO: 구글맵API 사용 시, 필요한 컬럼만 정리 필요
    private String roadAddress;

    // 상세 주소
    // TODO: 구글맵API 사용 시, 필요한 컬럼만 정리 필요
    private String detailAddress;

    // 한줄소개
    private String bio;

    // 프로필이미지ID
    private Long profileImageId;

    // 첨부파일ID
    private Long fileId;

    // 계정 상태
    private UserStatus status = UserStatus.PENDING;

    // 매니저 업무 가능 시간
    private List<AvailableTimeRspDTO> availableTimes;

    // 삭제 여부
    private Boolean isDeleted = false;
}
