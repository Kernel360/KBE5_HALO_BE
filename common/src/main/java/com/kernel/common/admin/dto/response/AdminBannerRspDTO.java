package com.kernel.common.admin.dto.response;

import com.kernel.common.global.enums.BannerStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class AdminBannerRspDTO {

    // 배너 ID
    private Long bannerId;

    // 배너 제목
    private String title;

    // 배너 경로
    private String path;

    // 배너 게시 시작 날짜
    private LocalDate startAt;

    // 배너 게시 종료 날짜
    private LocalDate endAt;

    // 배너 조회수
    private Long views;

    // 배너 첨부파일 ID
    private Long fileId;

    // 생성 정보
    private LocalDateTime createdAt;
    private Long createdBy;

    // 배너 게시 상태
    private BannerStatus bannerStatus;
}
