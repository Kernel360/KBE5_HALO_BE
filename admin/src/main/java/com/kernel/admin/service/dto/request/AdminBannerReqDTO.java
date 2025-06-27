package com.kernel.admin.service.dto.request;

import com.kernel.admin.domain.entity.Banner;
import com.kernel.admin.validator.ValidBannerPeriod;

import com.kernel.global.domain.entity.File;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
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

    @NotNull(message = "배너 첨부파일 ID는 필수입니다.")
    private Long fileId;

    // dto -> entity 변환 메서드
    public static Banner toEntity(AdminBannerReqDTO reqDTO, File file) {
        return Banner.builder()
                .title(reqDTO.getTitle())
                .path(reqDTO.getPath())
                .startAt(reqDTO.getStartAt())
                .endAt(reqDTO.getEndAt())
                .file(file)
                .build();
    }

}
