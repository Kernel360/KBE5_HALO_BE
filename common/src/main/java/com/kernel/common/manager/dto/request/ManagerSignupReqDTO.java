package com.kernel.common.manager.dto.request;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.kernel.common.global.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonNaming(PropertyNamingStrategies.LowerCamelCaseStrategy.class)
public class ManagerSignupReqDTO {

    // 연락처(=계정ID)
    @NotBlank(message = "연락처는 필수 입력입니다.")
    @Size(max = 20)
    private String phone;

    // 이메일
    @NotBlank(message = "이메일은 필수 입력입니다.")
    @Email(message = "유효한 이메일 형식이 아닙니다.")
    @Size(max = 50)
    private String email;

    // 비밀번호
    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상이어야 합니다.")
    private String password;

    // 이름
    @NotBlank(message = "이름은 필수 입력입니다.")
    @Size(max = 100)
    private String userName;

    // 생년월일
    @NotNull(message = "생년월일은 필수 입력입니다.")
    private LocalDate birthDate;

    // 성별
    @NotNull(message = "성별은 필수 입력입니다.")
    private Gender gender;

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
    // TODO: 첨부파일 완료되면 NotNull
//    @NotNull(message = "프로필 이미지는 필수입니다.")
    private Long profileImageId;

    // 첨부파일ID
    // TODO: 첨부파일 완료되면 NotNull
//    @NotNull(message = "첨부파일은 필수입니다.")
    private Long fileId;

    // 매니저 업무 가능 시간
    @NotNull(message = "업무 가능 시간 목록은 필수입니다.")
    @Size(min = 1, message = "최소 1개의 업무 가능 시간을 입력해주세요.")
    private List<AvailableTimeReqDTO> availableTimes;
}
