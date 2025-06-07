package com.kernel.common.manager.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ManagerUpdateReqDTO {

    // 이메일
    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Size(max = 50)
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // 위도
    @NotNull(message = "위도는 필수 입력입니다.")
    private Double latitude;

    // 경도
    @NotNull(message = "경도는 필수 입력입니다.")
    private Double longitude;

    // 도로명 주소
    @NotBlank(message = "도로명 주소는 필수 입력입니다.")
    @Size(max = 200)
    private String roadAddress;

    // 상세 주소
    @NotBlank(message = "상세 주소는 필수 입력입니다.")
    @Size(max = 100)
    private String detailAddress;

    // 한줄소개
    @NotBlank(message = "한줄소개는 필수 입력입니다.")
    @Size(max = 50)
    private String bio;

    // 프로필이미지ID
    @NotNull(message = "프로필 이미지는 필수입니다.")
    private Long profileImageId;

    // 첨부파일ID
    @NotNull(message = "첨부파일은 필수입니다.")
    private Long fileId;

    // 매니저 업무 가능 시간
    @NotNull(message = "업무 가능 시간 목록은 필수입니다.")
    @Size(min = 1, message = "최소 1개의 업무 가능 시간을 입력해주세요.")
    private List<AvailableTimeReqDTO> availableTimes;
}
