package com.kernel.common.admin.dto.request;

import com.kernel.common.admin.validator.ValidBannerPeriod;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@ValidBannerPeriod  // 커스텀 검증 어노테이션: 배너 게시 마감일이 시작일 이후인지 확인
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AdminBannerReqDTO {

    @NotBlank(message = "배너 제목은 필수입니다.")
    @Size(max = 50, message = "배너 제목은 최대 50자까지 입력 가능합니다.")
    private String title;

    @NotBlank(message = "배너 경로는 필수입니다.")
    @Size(max = 255, message = "배너 경로는 최대 255자까지 입력 가능합니다.")
    private String path;

    @NotNull(message = "게시 시작일 지정이 필요합니다.")
    @Future(message = "게시 시작일은 현재 날짜 이후여야 합니다.")
    private LocalDate startAt;

    @NotNull(message = "게시 종료일 지정이 필요합니다.")
    @Future(message = "게시 종료일은 게시 시작 날짜 이후여야 합니다.")
    private LocalDate endAt;

    //@NotNull(message = "배너 첨부파일 ID 지정이 필요합니다.")
    private Long fileId;


}
